package org.bluebridge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author think
 * @desc
 * @date 2026/1/27 16:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AepTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testAep(){
        String a1Command = "aAGXAIAEAAABoYgAdJeEvUaW4ZAmZws0UTRW1iprGozr0NteOy9kiL5vLdgDA3qDtvBgCdmysilyL5r3AwN6g7bwYAnZsrIpci+a9wMDeoO28GAJ2bKyKXIvmvds2QsCcPQYmDtRdZInWZGI5bH0kUce6zg0/UOdj/9VO8FgvfIdbrUSfsuh6gnOK5lxlKLLduGVHjahyc8CrjcG";
        sendAepCommand(a1Command, "a1Command");
        String ea0Command = "aAE8AIEEAAAB6oiAIMmeBvNqHwPQbsLQvcdWDjmG2sE4ZzE8MIzSoPmNsH2lhH4VNzREBbtHWtamrrYt";
        sendAepCommand(ea0Command, "ea0Command");
        String ea1Command = "aAEaAoIEAAAB6oiAXdrPHlWR2qLPdtysXMakamvuR6RPYx3TV9nHH+tKjiv/RUNBdf7vJ8g/2OroLK13rZ+WM91/UIEiJ+HanOqn2vpNoy5Q0iDug/BVWmV0dfFC9ytIRaMLzjRX/71iKOBeEbNV4w6FUaRMaUCalBwLTRE7DE3aitpVKP9ktDhASyYURN40NjGVJq0GY0UN2JS4t6Z3I9dvdVmYuNh2QH5BUZAv8zANPcfMgHWY3p0VBkS89LGlkOPNJtYsFS4rT3Af+z625FSxPjOSMgeN1P640mrpOLdL2JAp39ejNRhJCi2V064l3FQZ6BnHYBz8pyTK6ph7pP9Muz1K/P7ZGTufCkDBL7fcUdI+RTMaP6JGsOFbQk79IJVEZ975ICPWkVIJBa2HVpwNRVWSUFvdccdWZvLdP7oNX4D4Waf3SJLoKTYC2elINMl+6tZkv1Ip2v4kT2E2I+Quab/oPL/9FShXfrvSQx4YTbPezQTCqXDwy+Zs+fkEj6JRsHy3SG+wEIXFdPXgH0CLH6UCWnPKyN7qqcah+yKKOQ7CXzCXKKjg69d6yv2YCYNua9bkwQRNCBjcF4E0j/uydjR7q7OMzJ3OPkrX+JuXxAEXA33LdZHpJjSNV9gGL6t0jlefqp/l7sMV4dhIphWG5ZWR1u07jaEQmFfcoairecmkZcYtnKTejMPk2zFND4ngqDdzMmuiSh4z";
        sendAepCommand(ea1Command, "ea1Command");
        String ea2Command = "aAEaAoMEAAAB6oiAShNZB1vV7NrJu7AZ2E/KBDS/knXe8Tf4jsVCmXqQw+i/j+PhFI6wch/NS8GBshRo8BRjRSut102tyGIL1kCuW5TVx9V9I21bBjDuDsaceiiCSPpHTjt42w+L023sn0lqZ8JnAkdPUpmeJsRyH1e2B7t6yV1MPW9I9L/VB7gZllDxIL90r5DfXTx6T0FTwrFFQNiTp0cfbS4H8GvaicxW31cCMGiXZ2/pibehynJVi1Y55KVW3Io5UBLZ4uV3UhjRQqUaSR8V0gG0OE1iuMeUGbfBi75jWd95HIsWw1PhmO77kG4+psa3po8H/IuLJKnySRrK9yo/dO1Ki2T3ZI61GevafpHSnOtU7uATebwKLMtuhfipAcqRdMSM+K/rF6tim9nrO7YjJdD70C+RnH8ET79HfEg3b1ftDRSm5K78YJKD9I4NO8IKgZBxNAHzKtyT1qwN1V4f/ttyHgxRuMrInu7cwtRS6WXevmpdbBX/cYvajov/fpRlV3LCeImc8h9L3+CDAIFTrzOnW0p4aZUY0H2I6JpJPprCH60IEU13sso/m1OZfE3cTtp//c6T1ROm5OH7SIpGW3lfM7SjPa7De5m9rhxZJJ819tUt39XlkaSyN6o8jzlxBoF59fW8XYs/DZcM0HIticD8aXlaHM3l0mIiQ1Q2QXz5IYoyqGu6xRQCWiDboGOjLV1RJD6eqrZt";
        sendAepCommand(ea2Command, "ea2Command");
        String ea3Command = "aAEaAoQEAAAB6oiAfC71FOksFErRSKMmueOK3+AGQ7WK5OpA0KG7N9EbvgYYe6vMcEm0yDiz4PUIqt59L/LzD4zfqXFW8pLFtBY7dmPGsMEb6+LJ99XTvmWD+8dQoxPbID7SEf5rEUyp/Fhj9WUNOXNiGi1nHKqDGx9RINXPRpHRbqQqhdNuR3MjNT419eTJWUY6fOXfLSGvwSHNE/yxVR5BkzXgbqRSz0eWWVxVKAvabfovTus61rlEsW9g1lt03yemOXl46brYNlJLl/q6uen34r2d/RIubJuhVc0R4q75WzXCdsZ5n+Fnwu3Lzg/yk+CmunyYQm7eSyD4pQ/qVS95vA8sb1fSLwsp22dc/7W6lbHv9/OqzdlqEE/77PBUe+zSBStnOQ4911dqKgF10a7gEnZdbP+HIs5jY7iRtR0IFxKP4yHDlr17kbpGyyOVmBxnCxy9ldfNTvjTZPQ/GjBu67DlhP5QXsob7WPXJ/1FixVydUEBlBOMSjHZBS5Weut7z9kCu31VX3azVGpQGr8T90q4ZIQ+qwz4CxO5L2TqtWvfkXvYCgBKP3XNXpdsrnTvx5hzqPlju97xMN1lOQkxC2hL9Odqk/fgJDQH92csuSEzpXBBnFwrmeXLzmRQ/g26BFch6usGe4lCWI/OONKVbg3i9368ox+f4GJt4IppxOyRpu0+fI61dN29lq6QeQ7Ua2WproMk6gzK";
        sendAepCommand(ea3Command, "ea3Command");
        String ea4Command = "aAEaAoUEAAAB6oiAVvopQQ8+r2SpFvkgNZed2Zms+NqEhLy+I0Y6faxOqKKpd8spdIi6qPWB6feKOYQharjVuFL695MIMMPc7gwdnWBPdXPlUQ+M2WiVWMu3jrYyGlL+DAND+d3KwoCmLlM6lJnGoD3Tg/h4VF098HqwzuLMNX9iNQMDMaLXf/J5/CDUnLtPBdp4JF0xkypnGKj/UBClp9/r2/2OQYySukYAc/v8MlzdhD+zw5xtE2TLREAaDxW54VwAJWisZDu94aIpKi3go1BKCK7vvmt0a2bgHkj3qZlNJs5KVYIZYUSzyI5vvFy85BkTGa1RlVzb+JxPSOHDUt4fgHu8KFWdZT2Mp2LWPjfwTos7et5hbjNNg8R+uupog6qRXOvbFIbSG2F+kD5ajgkftz7SR21EXHFBk1CNyC6nhPo5+/lZfo6yU1Gr4uBeBHjQb/hFlVUkpMiPPBKwLagjeH2Z0QkSHya1jagS7hl9sJXdaanOOEUismgpS7JC1Ed8LdCfBy8AZ3CF1rdqWbsEDQsii2/Z13J735F8eHY1Qn8Us86tPOUIlAl7yvxvp5ePQB/n4PxsVT6NG6PhZh+M+KRD1EebbMbYqSMOxPQc8fCVtEwruMJvQmGCEiMZoV38WFqdxG6NZc/zKYkmx/4mlbXQY9k/GgZcJdfbYe+GFXW1gAtMonUAHOFhNGnbDOFc5Ql74TE7lwH4";
        sendAepCommand(ea4Command, "ea4Command");
        String ea5Command = "aAEaAoYEAAAB6oiAzXP6NTv/wHtY6PG0dubFgEemJSIqWHmYfHyUTeIPLLwPXhURwc/tsWK0O+euciY+VFbuN4sTMw9W7yEEIXVR85dRfceank1JnHYMo8s8fT5ps3yBBZJcIgCWcVW9iHk70O+exKPJ41TXvRLnb5T0h1a3+If5IfA5eKactHSfzXPA/ai54xD0ru8MsHwEy6N1RMUdBnxyhOhhrj2hO0Ez2d3oHNfEwc5APHmyYo4qZkQEov2IikooKhaLVzU54NtBSVMDhTGAK1BltCNtAGZFj/oyHY/Pw7Jmf02qf8EyOIGQv6t83c2DJBWgnEbubu2hZOVd/Nmdc9z+R8PkqeTvQGTmf0si5YwDQWbtG7Lfwe5SnJgdL0BsKoi5BP6sDIKWLDJ3tI0nl4Q+MWv/Y8JLCLNsAGyOx58L4rioKHT5iz5dSiE9flrku390cGEbppApLzJYY+d+t/kVH2J34NVoSugRBqx8ExJrKH2IonU6HGQdge9vR4ApY/lJ0mpIcm9yHfmWASg+ll2B3zmPZepUuS2IhDCN/RLs8c3KidxMTVr2otCruv/4ooalON+9Hl7pqXrgRrpuJ6A9bZUzkJLY987JrU2WcQBURf2rzYfbd5KpAsWOl4uDNB1OI7FeSFj2xm0BCH0PkpYEDCmIO6TnodHwY9IAmKEUoevAc/vst+c65Tv6FIisdjoPKYhhLc/H";
        sendAepCommand(ea5Command, "ea5Command");
        String ea6Command = "aAEaAocEAAAB6oiAG/eOrs2IPZxrMWr71g7XZY0MofZ9kBMGTZm5MnMOFDeZGl0wjqRv8t+7+dHfKzb39KiBf5vuPNH1benq3EvAScTH5luc5KVFAHAiA5DTTtcBqMIIqLSGxB4Ptbhx6GKSdSqDn/gVbD9EIJ7bJ2b0EFgqMLWYBlXM3gfWO2fHxhvx4Z20MsTDPmLXgf2L570SQbiH983If8xfBk0TlVG+5wqOH3F30FwGoXT626FAgAXRzdm/wXnYIIQl+qI4ESUm8cOUMLh/XpYUQ2VPAcda1cGAV4szb26Waq3/JeSrvIjOrdJSrQidmtS6+3OftJTFhKTr8GqHq7gTj+fTPyAStroMqxAFbMPkygE+Eqi1drxcQrUKvKnvlNezCISeCatQGEtvZW7kQWWQse3cuWAQF+RGFQHIck2s3w/B9DcMyKgA1sVWdZCVUPR4kOH4hhhSByslIkPHiBpozocjGjNQh3N3fp4F9WSEivO7uKp6+kWZTBZSKj+3g7FsnopnSy5SPdhK9hKU86EDE3r97NaSUCb5Jy8//bzS41vXNwJPWHppCwhvi0aY9KvEjqeLUPzkw5Wv+ZsZ+HqJZ0u4zRZBSF3tpGXjHgZnQQHdXiusUa5GdtZ5f5yc/Kig/jxQ7/Y/MloBpz7BWmBjjBqTBnhVKhLIWGnfEZW24S/z6gZcdNFdCd+2emBdXu11m75RLS/f";
        sendAepCommand(ea6Command, "ea6Command");
        String ea7Command = "aAEaAogEAAAB6oiAKQZHUD4M6dClsYGyWkHQTiAQAhfGN43EwiVucbHwMFSPURzwOm8+iM8ARzN7p2BGxinR8G3XSUum193ptH2y2hIuKDUTaU4X22+4jorHIQeH/6AoTXcwfn63sXPla3OmVCN+q+0mc3ftVNRYiIFOtSpKwzUZuDDDoF9eN8JgPHQjxgixVGbpxzq7s99yTpjxqEPsuEiaT69cx3iv+X300xDuaybd0l/wHHxtrZuDJhMibnEo9cukx4Nac3d4Bu8beK0w22rblvjUW0FhXwF3+SUt4x0+u9ATfUsNWTO3U8WZHKl4oky8JyDp8AgrIpkCD9OpVVK9Voykb2Nxpn8U6bY/49qC6yhVoq/IGAeJ+c37Hc3CAeRqzyi8jqQp0/CgqrdnZL7S25tsy65tRZdZZNwr8DahSdB1LDi3CcsceMBwjCXMRo9PVElvU9oRZ5zfZLJ8rDYmhysKcG6yK6Kx99XtWmVu4AONNlXhhsRldkxOeV8oDZm4KqWtQh96lBLjYSdlgHglKEukujX6yzGqHVuFHe0/CGfp6nXuXALwS3RtCXNozryJL3lqW9AFDzIGl0wEi35QkpJu/MR7Xgi2iwhUDvJ6DWIarpGV6QMGLGbtdgB0syjZ84b9V68KOESLY7Pm0bFjcEzR5TVm37eEGk10t7eAVb5EEYAJ6aUONaKn+aLeBwVKwxsHFHpU0XAA";
        sendAepCommand(ea7Command, "ea7Command");
        String ea9Command = "aAG6AYoEAAAB6ogAD/rWtcaMK2oXDvr6R0b2KAeG+DZALXdo3EOWIe6eUpKzwwCOvXxQaYXaQnj3ljUPhTgNksZIC8hIctVj7bNg9nrHPKSWE61VSym62rAXBr4DA3qDtvBgCdmysilyL5r3PzfvJpVbY4OsUF3o4wwgL1gPbDzgmCnbnR2OQJAcl3U7j22ar3m9G8Vs4thite0B6XX26S1nE/WOMHD5DDcaI3mj5NaelENOs14LL6YM2UknVL+hhiBflh2JC4xYC3UeT/PFG2YvUN5hMv0oSL0+6zj4poGSqADrMJAD6ZebGbLmnXqZrxuSAacQ6ohbhFte3iKePdSUQmESJOaM8hOFbeh1cGL0xUjgLqwL0t1xkW8f78qxpWisSIK2RYP9dgZvrKmx+tA2+8q3osplTV8GldtMqZWpvSsBA2PalE4mo+mQz3hy0UKBrTkSSfcGgD4eumr0+B2apuM1V/5C4MjECo96fGbMOfaMHb3LRHTTRPYxVfKtES0b/FwDS60oeaDsJc1Uuyiklv/VHWLcTkjaLwMDeoO28GAJ2bKyKXIvmvcOBfMh1Ex0Tdw0XYlPxYhm";
        sendAepCommand(ea9Command, "ea9Command");
        String ea10Command = "aAEeAIoEAAABo4gAGsxzW8suP2SBmpDW2jPnEYFabLk4z9p4GN9tGMLe9hY=";
        sendAepCommand(ea10Command, "ea10Command");

//        String ea8Command = "aAG6AYkEAAAB6ogAD/rWtcaMK2oXDvr6R0b2KAeG+DZALXdo3EOWIe6eUpKzwwCOvXxQaYXaQnj3ljUPhTgNksZIC8hIctVj7bNg9nrHPKSWE61VSym62rAXBr4DA3qDtvBgCdmysilyL5r3PzfvJpVbY4OsUF3o4wwgL1gPbDzgmCnbnR2OQJAcl3U7j22ar3m9G8Vs4thite0B6XX26S1nE/WOMHD5DDcaI3mj5NaelENOs14LL6YM2UknVL+hhiBflh2JC4xYC3UeT/PFG2YvUN5hMv0oSL0+6zj4poGSqADrMJAD6ZebGbLmnXqZrxuSAacQ6ohbhFte3iKePdSUQmESJOaM8hOFbeh1cGL0xUjgLqwL0t1xkW8f78qxpWisSIK2RYP9dgZvrKmx+tA2+8q3osplTV8GldtMqZWpvSsBA2PalE4mo+mQz3hy0UKBrTkSSfcGgD4eumr0+B2apuM1V/5C4MjECo96fGbMOfaMHb3LRHTTRPYxVfKtES0b/FwDS60oeaDsJc1Uuyiklv/VHWLcTkjaLwMDeoO28GAJ2bKyKXIvmvet9qiDQ8GQ2rDt37/A1TtU";
//        sendAepCommand(ea8Command, "ea8Command");
    }

    /**
     * 发送 AEP 命令
     * @param appData     命令数据
     * @param commandName 命令名称
     */
    private void sendAepCommand(String appData, String commandName) {
        String url = "http://localhost:8100/camera-recognition/report/data/aep";

        // 1. 设置请求头，明确指定为 JSON
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. 构造请求体
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("upPacketSN", -1);
        requestData.put("upDataSN", -1);
        requestData.put("topic", "v1/up/ad");
        requestData.put("timestamp", 1755659435776L);
        requestData.put("tenantId", "10494224");
        requestData.put("serviceId", "");
        requestData.put("protocol", "lwm2m");
        requestData.put("productId", "17124246");

        Map<String, String> payload = new HashMap<>();
        payload.put("APPdata", appData);
        requestData.put("payload", payload);

        requestData.put("messageType", "dataReport");
        requestData.put("deviceType", "");
        requestData.put("deviceId", "199999999999999991");
        requestData.put("assocAssetId", "");
        requestData.put("IMSI", "undefined");
        requestData.put("IMEI", "864294058768791");

        // 3. 将数据和头信息包装进 HttpEntity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

        try {
            // 4. 使用 RestTemplate 发送 POST 请求
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
            System.out.println("Sending Command ===> " + commandName);
            System.out.println("Response Status: " + responseEntity.getStatusCode());
        } catch (Exception e) {
            System.err.println("Request failed: " + e.getMessage());
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}