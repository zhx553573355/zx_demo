package zx.demo.test;


import zx.demo.service.FileDownload;

public class Test {

	public static void main(String args[]) throws Exception {
		FileDownload file = new FileDownload();
		 file.downloadNetFile("http://118.194.48.15:8080/kp_test/car_show_image/B/宝马/宝马(进口)/Vision Future Luxury.jpg",
		 "E:\\777.jpg");
}
}
