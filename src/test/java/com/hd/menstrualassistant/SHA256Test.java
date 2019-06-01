package com.hd.menstrualassistant;

import com.hd.menstrualassistant.utils.SHA256Util;
import org.junit.Assert;
import org.junit.Test;

public class SHA256Test {

    @Test
    public void testSha256(){
        //按照腾讯云短信文档中的例子进行测试
//        string sig = sha256(appkey=5f03a35d00ee52a21327ab048186a2c4&random=7226249334&time=1457336869&mobile=13788888888)
//                = ecab4881ee80ad3d76bb1da68387428ca752eb885e52621a3129dcf4d9bc4fd4;
        String str = "appkey=5f03a35d00ee52a21327ab048186a2c4&random=7226249334&time=1457336869&mobile=13788888888";
        String sha256Str = "ecab4881ee80ad3d76bb1da68387428ca752eb885e52621a3129dcf4d9bc4fd4";
        Assert.assertEquals(sha256Str, SHA256Util.SHA256(str));
    }

}
