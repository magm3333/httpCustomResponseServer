package ar.com.magm.teaching.expression;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import ar.com.magm.teaching.http.HttpResponse;

public class SelectFileExpression implements Expression {
	private String params;

	public SelectFileExpression(String params) {
		super();
		this.params = params;
	}

	@Override
	public byte[] getContent() {
		final String[] exts = params.toLowerCase().split(",");
		JFileChooser jfs = new JFileChooser();
		jfs.resetChoosableFileFilters();
		jfs.removeChoosableFileFilter(jfs.getFileFilter());
		jfs.addChoosableFileFilter(new FileFilter() {
			public boolean accept(File f) {
				boolean ok = false;
				for (String ext : exts) {
					if (f.getName().toLowerCase().endsWith(ext)) {
						ok = true;
					}
				}

				return ok || f.isDirectory();
			}

			public String getDescription() {
				return "Archivo tipo: " + params;
			}
		});

		jfs.setMultiSelectionEnabled(false);
		jfs.showOpenDialog(null);
		byte[] r=new byte[0];
		File selFile = jfs.getSelectedFile();
		if (selFile != null) {
			try {
				//TODO detectar mime type
				r = Files.readAllBytes(selFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	@Override
	public String getString() {
		return null;
	}

	@Override
	public String getFullExpression() {
		return null;
	}

	@Override
	public boolean isFullBody() {

		return true;
	}

	@Override
	public void reconfigure(HttpResponse response) {
		//TODO set mime type
	}

}
