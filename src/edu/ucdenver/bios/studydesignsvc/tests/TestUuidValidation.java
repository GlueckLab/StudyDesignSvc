package edu.ucdenver.bios.studydesignsvc.tests;

import java.math.BigInteger;
import java.util.UUID;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.ucdenver.bios.webservice.common.uuid.UUIDUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TestUuidValidation.
 */
public class TestUuidValidation extends TestCase {

    /** The bint. */
    BigInteger bint = null;
    // 1999999991;

    /** The STUD y_ uuid. */
    private static UUID STUDY_UUID = UUID
    // .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");
            .fromString("66ccfd20-4478-11e1-9641-0002a5d5c51a");

    /** The uuid. */
    private static byte[] uuid = null;

    /** The flag. */
    static boolean flag;

    /*
     * Test for UUID validation
     */
    /**
     * Test validate.
     */
    @Test
    public static void testValidate() {
        System.out.println("\nInValid UUID Case");
        Gson gson = new Gson();
        System.out.println("Valid Uuid :\n\t" + STUDY_UUID);
        // valid uuid -> byte array
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        System.out.println("Valid Uuid Byte Array :\n\t" + gson.toJson(uuid));
        // forcefully change uuid // uuid[0]='!';
        uuid[0] = '#';
        System.out.println("Forcefully Changed Uuid Byte Array:\n\t"
                + gson.toJson(uuid));
        // changed byte array -> string
        String uuidString = UUIDUtils.bytesToHex(uuid);
        System.out.println("Changed Uuid :\n\t" + uuidString);
        // changed uuid string -> uuid
        uuid = UUIDUtils.hexToBytes(uuidString);
        System.out.println("Changed Uuid reconversion to Byte Array:\n\t"
                + gson.toJson(uuid));
        // flag =
        // Pattern.matches("/^[0-9a-fA-F]{8}[0-9a-fA-F]{4}[0-9a-fA-F]{4}[0-9a-fA-F]{4}[0-9a-fA-F]{12}$/",
        // uuidString);
        // regex for validating uuid
        flag = Pattern.matches("[0-9a-fA-F]{32}", uuidString);
        System.out.println(flag);
        // How to recover changed uuid?
    }

    /**
     * Test valid uuid test.
     */
    @Test
    public static void testValidUuidTest() {
        System.out.println("\nValid UUID Case");
        Gson gson = new Gson();
        System.out.println("Valid Uuid :\n\t" + STUDY_UUID);
        // valid uuid -> byte array
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        System.out.println("Valid Uuid Byte Array :\n\t" + gson.toJson(uuid));
        // byte array -> string
        String uuidString = UUIDUtils.bytesToHex(uuid);
        System.out.println("Uuid :\n\t" + uuidString);
        // uuid string -> uuid
        uuid = UUIDUtils.hexToBytes(uuidString);
        System.out.println("Uuid reconversion to Byte Array:\n\t"
                + gson.toJson(uuid));
        // flag =
        // Pattern.matches("/^[0-9a-fA-F]{8}[0-9a-fA-F]{4}[0-9a-fA-F]{4}[0-9a-fA-F]{4}[0-9a-fA-F]{12}$/",
        // uuidString);
        // regex for validating uuid
        flag = Pattern.matches("[0-9a-fA-F]{32}", uuidString);
        System.out.println(flag);
        // How to recover changed uuid?
    }

    /*
     * Testing
     */
    /**
     * Test uuid validation.
     */
    private static void testUuidValidation() {
        uuid = UUIDUtils.asByteArray(STUDY_UUID);
        Gson gson = new Gson();
        String uuidString = gson.toJson(uuid);
        System.out.println(uuidString);
        System.out.println(uuidString.length());
        // Pattern p = Pattern.compile("[,\\d|a-f|A-F]{16}");
        Pattern p = Pattern.compile("[,]+");
        String[] result = p.split(uuidString);
        // validate length : should be 17 i.e 16+1
        if (result.length == 16) {
            for (int i = 0; i < result.length; i++) {
                System.out.println("" + i + " : " + result[i]);
                if (i == 0) {
                    System.out.print("1 ");
                    // flag = Pattern.matches("\\[(\\-)?(\\d){1,3}", result[i]);
                } else if (i == result.length - 1) {
                    System.out.print("2 ");
                    // flag = Pattern.matches("(\\-)?(\\d){1,3}\\]", result[i]);
                } else {
                    System.out.print("3 ");
                    // flag = Pattern.matches("(\\-)?(\\d){1,3}", result[i]);
                }
                System.out.println(flag);
            }
        }
        System.out.println("final : " + flag);
        // @=64
        // =32
        // 0=48 9=57
        // a=97 f=102
        // A=65 F=70
        // System.out.println(UUIDUtils.asByteArray("F"));
    }
}
