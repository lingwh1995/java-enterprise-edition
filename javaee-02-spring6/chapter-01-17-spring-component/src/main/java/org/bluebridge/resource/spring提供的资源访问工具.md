# 1.Spring资源访问Resource接口简介
    Resource接口是Spring资源访问策略的抽象，它本身并不提供任何资源访问实现，具体的资源访问由该接口的实现类完成——每个实现类代表一种资源访问策略。
    Resource一般包括这些实现类：
        UrlResource(访问网络资源)
            Resource的一个实现类，用来访问网络资源，它支持URL的绝对路径。
            http        该前缀用于访问基于HTTP协议的网络资源。
            ftp         该前缀用于访问基于FTP协议的网络资源
            file        该前缀用于从文件系统中读取资源
        ClassPathResource(访问类路径下资源)
            就是meaven工程中resource下的文件
        FileSystemResource      访问文件系统资源
        ServletContextResource  访问文件系统资源
        InputStreamResource
        ByteArrayResource
# 2.ResourceLoader
    Spring将采用和ApplicationContext相同的策略来访问资源。也就是说，如果ApplicationContext是FileSystemXmlApplicationContext，res就是FileSystemResource实例；如果ApplicationContext是ClassPathXmlApplicationContext，res就是ClassPathResource实例
    当Spring应用需要进行资源访问时，实际上并不需要直接使用Resource实现类，而是调用ResourceLoader实例的getResource()方法来获得资源，ReosurceLoader将会负责选择Reosurce实现类，也就是确定具体的资源访问策略，从而将应用程序和具体的资源访问策略分离开来
    另外，使用ApplicationContext访问资源时，可通过不同前缀指定强制使用指定的ClassPathResource、FileSystemResource等实现类