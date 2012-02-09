package edu.ucdenver.bios.studydesignsvc.application;

import java.util.UUID;

public class UUIDConversion {
	private byte[] uuid = null;
	private UUID studyUUID = null;

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

	public byte[] getUuid() {
		return uuid;
	}

	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	public UUID getStudyUUID() {
		return studyUUID;
	}

	public void setStudyUUID(UUID studyUUID) {
		this.studyUUID = studyUUID;
	}

}
