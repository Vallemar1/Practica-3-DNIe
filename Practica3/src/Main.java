import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Main {
	static String name = null;
	static String dni = null;
	static ObtenerDatos data = null;
	static int option = 1;
	static int autentication = 1;

	public static void main(String[] args) throws Exception {
		Icon icon = new ImageIcon("logo_dni.jpg");
		Thread decodeDni;
		Thread frame;
	
		frame = new Thread(new Runnable() {

			public void run() {

				int opcion = JOptionPane.showOptionDialog(null,
						"Bienvenido al programa de authentificacion\noficial de DNIE.\n**Inserte su DNIE**", "DNIE",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[] {}, "sas");

			}

		});
		frame.start();

		Thread.sleep(1000);
		boolean isCard = false;
		while (isCard == false) {
			data = new ObtenerDatos();
			if (data.detectedCard()) {
				JOptionPane.getRootFrame().dispose();
				isCard = true;
			} else {
				Thread.sleep(10000);
			}

		}
		decodeDni = new Thread(new Runnable() {
			boolean correct = false;

			public void run() {
				while (correct == false) {
					correct = data.decodeDni();

			System.out.println(correct);
					
					name = ObtenerDatos.name;
					dni = ObtenerDatos.dni;
					System.out.println(name);
					System.out.println(dni);
				}
			}

		});
		decodeDni.start();

		String passUser = (String) JOptionPane.showInputDialog(null, "Introduzca su Contraseña:", "DNIE",
				JOptionPane.YES_NO_OPTION, icon, null, "");
		if (passUser == null)
			autentication = 0;

		while (autentication != 0 && autentication != -1) {

			autentication = JOptionPane.showOptionDialog(null, "Opciones de Authenticacion", "DNIE",
					JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, icon,
					new Object[] { "Salir", "Metodo Get", "Metodo Post" }, "sas");
			try {

				switch (autentication) {

				case 1:
					if (new GetRequest().sendGet(name, dni, passUser)) {

					} else {

					}
					break;
				case 2:

					if (new PostRequest().sendPost(name, dni, passUser)) {

					} else {

					}
					break;
				}
			} catch (ConnectException e) {
				e.printStackTrace();
				JOptionPane.showOptionDialog(null, "Parece que la pagina no esta disponible\t", "DNIE",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[] { "Ok" },
						"sas");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showOptionDialog(null, "Parece que la pagina no esta disponible\t", "DNIE",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[] { "Ok" },
						"sas");
			}

		}

		JOptionPane.showOptionDialog(null, "Hasta Otra!!\t", "DNIE", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, icon, new Object[] { "Ok" }, "sas");

	}

}
