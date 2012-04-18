package edu.ucdenver.bios.studydesignsvc.application;

import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class UUIDConversion.
 */
public class UUIDConversion {
	
	/** The uuid. */
	private byte[] uuid = null;
	
	/** The study uuid. */
	private UUID studyUUID = null;

	/**
	 * As byte array.
	 *
	 * @param uuid the uuid
	 * @return the byte[]
	 */
	public static byte[] asByteArray(UUID uuid) {
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		byte[] buffer = new byte[16];

		for (int i = 0; i < 8; i++) {
			buffer[i] = (byte) (msb >>> 8 * (7 - i));
		}
		for (int i = 8; i < 16; i++) {
			buffer[i] = (byte) (lsb >>> 8 * (7 - i));
		}

		return buffer;

	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public byte[] getUuid() {
		return uuid;
	}

	/**
	 * Sets the uuid.
	 *
	 * @param uuid the new uuid
	 */
	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the study uuid.
	 *
	 * @return the study uuid
	 */
	public UUID getStudyUUID() {
		return studyUUID;
	}

	/**
	 * Sets the study uuid.
	 *
	 * @param studyUUID the new study uuid
	 */
	public void setStudyUUID(UUID studyUUID) {
		this.studyUUID = studyUUID;
	}

}
