#!/usr/bin/env python3
import argparse
import os
import sys
from urllib.parse import quote

import requests


def build_webhdfs_url(namenode_http, hdfs_path, user, overwrite, permission, replication, blocksize):
    """
    构建 WebHDFS 创建文件的请求 URL

    :param namenode_http: NameNode HTTP 地址，例如 http://hadoop21:9870
    :param hdfs_path: HDFS 目标路径，例如 /input/input.txt
    :param user: HDFS 用户名
    :param overwrite: 是否覆盖已存在的文件
    :param permission: 文件权限，例如 755
    :param replication: 副本数
    :param blocksize: 块大小（字节）
    :return: 完整的 WebHDFS CREATE 请求 URL
    """
    base = namenode_http.rstrip('/')
    encoded_path = quote(hdfs_path, safe='/')
    params = [
        'op=CREATE',
        f'overwrite={str(overwrite).lower()}',
        'createparent=true',
    ]

    if user:
        params.append(f'user.name={quote(user)}')
    if permission:
        params.append(f'permission={quote(permission)}')
    if replication:
        params.append(f'replication={replication}')
    if blocksize:
        params.append(f'blocksize={blocksize}')

    return f'{base}/webhdfs/v1{encoded_path}?{"&".join(params)}'


def upload_file(namenode_http, local_file, hdfs_path, user=None, overwrite=True,
                permission=None, replication=None, blocksize=None, timeout=60):
    """
    上传单个本地文件到 HDFS（两步：NameNode 创建 → DataNode 写入）

    :param namenode_http: NameNode HTTP 地址，例如 http://hadoop21:9870
    :param local_file: 本地文件路径
    :param hdfs_path: HDFS 目标路径
    :param user: HDFS 用户名
    :param overwrite: 是否覆盖已存在的文件
    :param permission: 文件权限，例如 755
    :param replication: 副本数
    :param blocksize: 块大小（字节）
    :param timeout: 请求超时时间（秒）
    """
    if not os.path.isfile(local_file):
        raise FileNotFoundError(f'本地文件不存在: {local_file}')

    create_url = build_webhdfs_url(
        namenode_http=namenode_http,
        hdfs_path=hdfs_path,
        user=user,
        overwrite=overwrite,
        permission=permission,
        replication=replication,
        blocksize=blocksize,
    )

    # 第一步：请求 NameNode 创建文件，NameNode 会返回 307，并在 Location 中给出 DataNode 上传地址。
    resp = requests.put(create_url, allow_redirects=False, timeout=timeout)

    if resp.status_code not in (307, 201):
        raise RuntimeError(
            f'NameNode 创建请求失败，状态码: {resp.status_code}\n'
            f'URL: {create_url}\n'
            f'响应: {resp.text}'
        )

    if resp.status_code == 201:
        return

    datanode_url = resp.headers.get('Location')
    if not datanode_url:
        raise RuntimeError('NameNode 返回 307，但响应头中没有 Location')

    file_size = os.path.getsize(local_file)
    headers = {
        'Content-Type': 'application/octet-stream',
        'Content-Length': str(file_size),
    }

    # 第二步：把文件内容 PUT 到 DataNode。
    with open(local_file, 'rb') as f:
        resp = requests.put(
            datanode_url,
            data=f,
            headers=headers,
            allow_redirects=False,
            timeout=timeout,
        )

    if resp.status_code != 201:
        raise RuntimeError(
            f'DataNode 上传失败，状态码: {resp.status_code}\n'
            f'URL: {datanode_url}\n'
            f'响应: {resp.text}'
        )


def upload_file_to_hdfs(local_file=None, hdfs_path=None, namenode='http://hadoop21:9870',
                        user='hadoop', overwrite=True, permission=None,
                        replication=None, blocksize=None, timeout=60):
    """
    上传本地文件到 Hadoop HDFS 目录中

    :param local_file: 本地文件路径，为 None 时从命令行参数读取
    :param hdfs_path: HDFS 目标路径
    :param namenode: NameNode HTTP 地址
    :param user: HDFS 用户名
    :param overwrite: 是否覆盖已存在的文件
    :param permission: 文件权限，例如 755
    :param replication: 副本数
    :param blocksize: 块大小（字节）
    :param timeout: 请求超时时间（秒）
    """
    if local_file is None:
        parser = argparse.ArgumentParser(description='通过 WebHDFS 上传文件到 Hadoop HDFS')
        parser.add_argument('--local-file', default='./var/tmp/hadoop/input/input.txt', help='本地文件路径，默认 ./var/tmp/hadoop/input/input.txt')
        parser.add_argument('--hdfs-path', default='/input/input.txt', help='HDFS 目标路径，默认 /input/input.txt')
        parser.add_argument('--namenode', default='http://hadoop21:9870', help='NameNode HTTP 地址，默认 http://hadoop21:9870')
        parser.add_argument('--user', default='hadoop', help='HDFS 用户名，默认 hadoop')
        parser.add_argument('--no-overwrite', action='store_true', help='如果目标文件已存在则不覆盖')
        parser.add_argument('--permission', default=None, help='HDFS 文件权限，例如 755')
        parser.add_argument('--replication', type=int, default=None, help='副本数，例如 3')
        parser.add_argument('--blocksize', type=int, default=None, help='块大小，例如 134217728')
        parser.add_argument('--timeout', type=int, default=60, help='请求超时时间，单位秒，默认 60')
        args = parser.parse_args()

        local_file = args.local_file
        hdfs_path = args.hdfs_path
        namenode = args.namenode
        user = args.user
        overwrite = not args.no_overwrite
        permission = args.permission
        replication = args.replication
        blocksize = args.blocksize
        timeout = args.timeout

    try:
        upload_file(
            namenode_http=namenode,
            local_file=local_file,
            hdfs_path=hdfs_path,
            user=user,
            overwrite=overwrite,
            permission=permission,
            replication=replication,
            blocksize=blocksize,
            timeout=timeout,
        )
        print(f'上传成功: {local_file} -> {hdfs_path}')
    except Exception as e:
        print(f'上传失败: {e}', file=sys.stderr)
        sys.exit(1)


def upload_dir_to_hdfs(local_dir=None, hdfs_dir=None, namenode='http://hadoop21:9870',
                       user='hadoop', overwrite=True, permission=None,
                       replication=None, blocksize=None, timeout=60):
    """
    上传本地文件夹中所有文件到 Hadoop HDFS 目录中

    :param local_dir: 本地文件夹路径，为 None 时从命令行参数读取
    :param hdfs_dir: HDFS 目标目录
    :param namenode: NameNode HTTP 地址
    :param user: HDFS 用户名
    :param overwrite: 是否覆盖已存在的文件
    :param permission: 文件权限，例如 755
    :param replication: 副本数
    :param blocksize: 块大小（字节）
    :param timeout: 请求超时时间（秒）
    """
    if local_dir is None:
        parser = argparse.ArgumentParser(description='通过 WebHDFS 上传文件夹中所有文件到 Hadoop HDFS')
        parser.add_argument('--local-dir', default='./var/tmp/hadoop/input', help='本地文件夹路径，默认 ./var/tmp/hadoop/input')
        parser.add_argument('--hdfs-dir', default='/input', help='HDFS 目标目录，默认 /input')
        parser.add_argument('--namenode', default='http://hadoop21:9870', help='NameNode HTTP 地址，默认 http://hadoop21:9870')
        parser.add_argument('--user', default='hadoop', help='HDFS 用户名，默认 hadoop')
        parser.add_argument('--no-overwrite', action='store_true', help='如果目标文件已存在则不覆盖')
        parser.add_argument('--permission', default=None, help='HDFS 文件权限，例如 755')
        parser.add_argument('--replication', type=int, default=None, help='副本数，例如 3')
        parser.add_argument('--blocksize', type=int, default=None, help='块大小，例如 134217728')
        parser.add_argument('--timeout', type=int, default=60, help='请求超时时间，单位秒，默认 60')
        args = parser.parse_args()

        local_dir = args.local_dir
        hdfs_dir = args.hdfs_dir
        namenode = args.namenode
        user = args.user
        overwrite = not args.no_overwrite
        permission = args.permission
        replication = args.replication
        blocksize = args.blocksize
        timeout = args.timeout

    if not os.path.isdir(local_dir):
        print(f'本地文件夹不存在: {local_dir}', file=sys.stderr)
        sys.exit(1)

    files = [f for f in os.listdir(local_dir) if os.path.isfile(os.path.join(local_dir, f))]
    if not files:
        print(f'文件夹中没有文件: {local_dir}', file=sys.stderr)
        sys.exit(1)

    success_count = 0
    fail_count = 0

    for file_name in files:
        local_file = os.path.join(local_dir, file_name)
        hdfs_path = hdfs_dir.rstrip('/') + '/' + file_name

        try:
            upload_file(
                namenode_http=namenode,
                local_file=local_file,
                hdfs_path=hdfs_path,
                user=user,
                overwrite=overwrite,
                permission=permission,
                replication=replication,
                blocksize=blocksize,
                timeout=timeout,
            )
            print(f'上传成功: {local_file} -> {hdfs_path}')
            success_count += 1
        except Exception as e:
            print(f'上传失败: {local_file} -> {hdfs_path}，原因: {e}', file=sys.stderr)
            fail_count += 1

    print(f'\n上传完成: 成功 {success_count} 个，失败 {fail_count} 个')
    if fail_count > 0:
        sys.exit(1)


if __name__ == '__main__':
    # upload_file_to_hdfs(
    #     local_file='./var/tmp/hadoop/input/input.txt',
    #     hdfs_path='/input/input.txt',
    # )

    upload_dir_to_hdfs(
        local_dir='/Users/lingwh/Documents/学习资料/04_大数据开发课程/06.大数据技术之Hive/2.资料/02_data/Hive调优测试数据',
        hdfs_dir='/hive',
    )
