package util;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * User: SMironov Date: 01.04.2004 Time: 11:39:16
 * @author Mediaspectrum, Inc.
 */
public class LevelStore {
	private RecordStore recordStore = null;

	public LevelStore() {
		try {
			recordStore = RecordStore.openRecordStore("oppositeLevel", true);
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public int getLevel() {
		try {
			RecordEnumeration en = recordStore.enumerateRecords(null, null, true);
			if (en.hasNextElement()) {
				ByteArrayInputStream bais = new ByteArrayInputStream(en.nextRecord());
				DataInputStream inputStream = new DataInputStream(bais);
				return inputStream.readInt();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	public void setNewLevel(int level) {
		try {
			RecordEnumeration en = recordStore.enumerateRecords(null, null, true);
			while (en.hasNextElement()) {
				int recId = en.nextRecordId();
				recordStore.deleteRecord(recId);
			}
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bout);
			out.writeInt(level);
			byte[] b = bout.toByteArray();
			recordStore.addRecord(b, 0, b.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
