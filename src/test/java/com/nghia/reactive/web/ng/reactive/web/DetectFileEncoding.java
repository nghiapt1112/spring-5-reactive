package com.nghia.reactive.web.ng.reactive.web;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DetectFileEncoding {
    public static void main(String[] args) {

        detectFromFile();
        detectFromByte();
    }

    private static void detectFromFile() {
        try (
                FileInputStream fis = new FileInputStream("/mnt/project/Sources/NGHIA/PET/REACTIVE-Web/ng.reactive.web/src/test/resources/files/users.csv");
                InputStreamReader isr = new InputStreamReader(fis)) {
            System.out.println("Character Encoding: " + isr.getEncoding());
        } catch (Exception e) {
            // print error
            System.out.print("The stream is already closed");
        }
    }

    private static void detectFromByte() {
        try (FileInputStream fis = new FileInputStream("/mnt/project/Sources/NGHIA/PET/REACTIVE-Web/ng.reactive.web/src/test/resources/files/users.csv")) {
            byte[] nexwayByteContent = IOUtils.toByteArray(fis);
            InputStreamReader hehe = new InputStreamReader(new ByteArrayInputStream(nexwayByteContent));
            System.out.println("encoding: " + hehe.getEncoding());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
