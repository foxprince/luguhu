package cn.anthony.luguhu;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class Test {
	public static void main(String[] args) {
		URL source;
		try {
			source = new URL("https://mp.weixin.qq.com/s?__biz=MzIyNzA2NjE1OQ==&mid=2653291192&idx=1&sn=72f8cf48631380fbf013650952a1b78e&chksm=f3b44af8c4c3c3ee23d82eb433212388940094c206c9d33ca3656aa0014fb3e523c6d4c2f8cc#rd");
			File destination = new File("/Users/zj/tmp/gia.html");
			FileUtils.copyURLToFile(source, destination);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
