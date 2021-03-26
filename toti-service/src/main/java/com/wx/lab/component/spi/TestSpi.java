package com.wx.lab.component.spi;

import java.util.ServiceLoader;

/**
 * @author wangxiao@ybm100.com
 * @date 2020-11-03 14:15
 * @projectname totipotent
 */
public class TestSpi {

    public static void main(String[] args) {
        ServiceLoader<UploadCDN> uploadCDN = ServiceLoader.load(UploadCDN.class);
        for (UploadCDN u : uploadCDN) {
            u.upload("filePath");
        }
    }
}
