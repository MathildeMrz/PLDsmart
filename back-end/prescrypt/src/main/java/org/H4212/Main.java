package org.H4212;

import org.H4212.services.ServiceOCR;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ServiceOCR service = new ServiceOCR();
        service.generateJSON(null);
    }
}