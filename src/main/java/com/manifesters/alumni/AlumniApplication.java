package com.manifesters.alumni;

import com.manifesters.alumni.util.FileUploadUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class AlumniApplication {

	public static void main(String[] args) {
		new File(FileUploadUtil.getUploadDirectory()).mkdir();
		SpringApplication.run(AlumniApplication.class, args);
		System.out.println("hi ");
	}

}
