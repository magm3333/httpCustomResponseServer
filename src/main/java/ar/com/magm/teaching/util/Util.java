package ar.com.magm.teaching.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import ar.com.magm.teaching.http.HttpResponse;
import ar.com.magm.teaching.http.MimeType;

public final class Util {

	public static File getAndEnsureUserForlder() {
		File f = new File(System.getProperty("user.home"));
		File ff = null;
		if (f.exists() && f.isDirectory()) {
			ff = new File(f, ".chrsmagm");
		} else {
			ff = new File(System.getProperty("user.dir"), ".chrsmagm");
		}
		if (!ff.exists())
			ff.mkdir();
		return ff;
	}

	public static File[] getPreResponses() throws IOException {
		File f = new File(getAndEnsureUserForlder(), "preresponses");
		if (!f.exists()) {
			f.mkdir();
		}
		File[] r = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".http");
			}
		});
		if (r.length == 0) {
			createDefaults();
			r = f.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".http");
				}
			});
		}

		return r;
	}

	public static String[] getPreResponsesNames() throws IOException {
		File[] l = Util.getPreResponses();
		String[] r = new String[l.length];
		for (int t = 0; t < r.length; t++)
			r[t] = l[t].getName().substring(0, l[t].getName().length() - 5);
		return r;
	}

	public static ListModel<String> getPreResponsesNamesListModel() throws IOException{
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		for(String s:getPreResponsesNames()) {
			dlm.addElement(s);
		}
		return dlm;

	}

	public static void savePreResponse(String name, String content, JFrame parent) throws IOException {
		File f = new File(getAndEnsureUserForlder(), "preresponses");
		File ff = new File(f, name + ".http");
		boolean save = true;
		if (ff.exists()) {
			save = JOptionPane.showConfirmDialog(parent,
					"El archivo [" + name + "], ya existe, desea sobreescribirlo?") == JOptionPane.YES_OPTION;
		}
		if (save) {
			FileWriter fr = new FileWriter(ff);
			fr.write(content);
			fr.flush();
			fr.close();
		}
	}

	public static String getPreResponse(String name) throws FileNotFoundException {
		File f = new File(getAndEnsureUserForlder(), "preresponses");
		File ff = new File(f, name + ".http");
		Scanner s = new Scanner(new FileInputStream(ff));
		StringBuilder sb = new StringBuilder();
		while (s.hasNextLine()) {
			sb.append(s.nextLine() + "\n");
		}
		s.close();
		return sb.toString();
	}

	private static void createDefaults() throws IOException {
		HttpResponse resp = new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", MimeType.TEXT_PLAIN.mimeType());
		resp.addHeader("Connection", "close");
		resp.setBody("Hola desde HTTPCustomResponseServer - That's rock & roll!");
		savePreResponse("default Text Plain", resp.toString(), null);

		resp = new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", MimeType.HTML.mimeType());
		resp.addHeader("Connection", "close");
		resp.setBody(
				"<html><body><h3>Hola desde HTTPCustomResponseServer</h3><h4>That's rock & roll!</h4></body></html>");
		savePreResponse("default HTML", resp.toString(), null);

		resp = new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", MimeType.JSON.mimeType());
		resp.addHeader("Connection", "close");
		resp.setBody("{\"id\":1,\"value\":\"Hola desde HTTPCustomResponseServer - That's rock & roll!\"}");
		savePreResponse("default JSON", resp.toString(), null);

		resp = new HttpResponse();
		resp.addHeader("Server", "Custom HTTP Server 1.0");
		resp.addHeader("Content-Type", MimeType.XML.mimeType());
		resp.addHeader("Connection", "close");
		resp.setBody(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><message>Hola desde HTTPCustomResponseServer - That's rock &amp; roll!</message>");
		savePreResponse("default XML", resp.toString(), null);
	}

}
