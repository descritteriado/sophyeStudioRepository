package com.util.general;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Iterator;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

public class UtilsX {

	final static Logger logger = Logger.getLogger(UtilsX.class);

	/**
	 * Metodo que retorna el valor de las constantes como Integer
	 * @param constant
	 * @return
	 */
	public static Integer getAsInteger(String constant) {
		Integer val = null;
		try {
			val = Integer.parseInt(constant);
		} catch (Exception e) {
			logger.error(e);
		}

		return val;
	}

	/**
	 * Valida Identificacion
	 * 
	 * @param identificacion
	 * @return
	 */
	public static boolean validadorDeIdentificacion(String identificacion) {
		boolean identificacionCorrecta = false;

		try {

			if (identificacion.length() == 13) {
				identificacion = identificacion.substring(0, 10);
			}

			int tercerDigito = Integer.parseInt(identificacion.substring(2, 3));
			if (tercerDigito < 6) {
				// Coeficientes de validación cédula
				// El decimo digito se lo considera dígito verificador
				int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
				int verificador = Integer.parseInt(identificacion.substring(9, 10));
				int suma = 0;
				int digito = 0;
				for (int i = 0; i < (identificacion.length() - 1); i++) {
					digito = Integer.parseInt(identificacion.substring(i, i + 1)) * coefValCedula[i];
					suma += ((digito % 10) + (digito / 10));
				}

				if ((suma % 10 == 0) && (suma % 10 == verificador)) {
					identificacionCorrecta = true;
				} else if ((10 - (suma % 10)) == verificador) {
					identificacionCorrecta = true;
				} else {
					identificacionCorrecta = false;
				}
			} else {
				identificacionCorrecta = false;
			}

		} catch (NumberFormatException nfe) {
			identificacionCorrecta = false;
		} catch (Exception err) {
			logger.error(err);
			identificacionCorrecta = false;
		}
		return identificacionCorrecta;
	}

	/**
	 * Metodo para cifrar password
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static byte[] cifra(String sinCifrar) throws Exception {
		final byte[] bytes = sinCifrar.getBytes("UTF-8");
		final Cipher aes = obtieneCipher(true);
		final byte[] cifrado = aes.doFinal(bytes);
		return cifrado;
	}

	/**
	 *  Metodo para decifrar la contraseña 
	 * @param cifrado
	 * @return
	 * @throws Exception
	 */
	public static String descifra(byte[] cifrado) throws Exception {
		final Cipher aes = obtieneCipher(false);
		final byte[] bytes = aes.doFinal(cifrado);
		final String sinCifrar = new String(bytes, "UTF-8");
		return sinCifrar;
	}

	
	private static Cipher obtieneCipher(boolean paraCifrar) throws Exception {
		final String frase = "decifrar";
		final MessageDigest digest = MessageDigest.getInstance("SHA");
		digest.update(frase.getBytes("UTF-8"));
		final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

		final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		if (paraCifrar) {
			aes.init(Cipher.ENCRYPT_MODE, key);
		} else {
			aes.init(Cipher.DECRYPT_MODE, key);
		}

		return aes;
	}

	/*
	 * Metodo que convierte una cadena de bytes en una imagen JPG input: bytes:
	 * array que contiene los binarios de la imagen Output: la foto JPG en
	 * formato IMAGE
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private Image ConvertirImagen(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
		ImageReader reader = (ImageReader) readers.next();
		Object source = bis;
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		return reader.read(0, param);
	}

	/**
	 * Encola un mensaje informativo en el contexto JSF
	 * 
	 * @param summary
	 *            Resumen del mensaje
	 * @param detail
	 *            Detalle del mensaje
	 */
	public static void addInfoMessage(final String summary, final String detail) {
		addMessage(summary, detail, FacesMessage.SEVERITY_INFO);
	}

	/**
	 * Encola un mensaje de error en el contexto JSF
	 * 
	 * @param summary
	 *            Resumen del mensaje
	 * @param detail
	 *            Detalle del mensaje
	 */
	public static void addErrorMessage(final String summary, final String detail) {
		addMessage(summary, detail, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * Encola un mensaje en el contexto JSF.
	 * 
	 * @param summary
	 *            Resumen del mensaje
	 * @param detail
	 *            Detalle del mensaje
	 * @param severity
	 *            {@link Severity} Severidad del mensaje.
	 */
	private static void addMessage(final String summary, final String detail, final Severity severity) {
		final FacesMessage infoMessage = new FacesMessage();
		infoMessage.setSummary(summary);
		infoMessage.setDetail(detail);
		infoMessage.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, infoMessage);
	}
}
