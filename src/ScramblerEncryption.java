import java.io.*;
import java.math.BigInteger;

public class ScramblerEncryption {
    private static byte[] readFile(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static void writeFile(String fileName, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(data);
        fos.close();
    }

    private static byte[] generateKey(int length) {
        BigInteger first = BigInteger.valueOf(1);
        BigInteger second = BigInteger.valueOf(1);
        BigInteger third = BigInteger.valueOf(2);

        BigInteger maxValue = BigInteger.valueOf(Integer.MAX_VALUE);

        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(first).append(second).append(third);

        BigInteger a = first;
        BigInteger b = second;
        BigInteger c = third;

        while (keyBuilder.length() < length) {
            BigInteger next = a.multiply(b).multiply(c);
            if (next.compareTo(maxValue) >= 0) {
                break;
            }
            keyBuilder.append(next);
            a = b;
            b = c;
            c = next;
        }

        String keyString = keyBuilder.toString();
        byte[] key = new byte[length];
        for (int i = 0; i < length; i++) {
            key[i] = (byte) keyString.charAt(i);
        }

        return key;
    }

    private static byte[] applyScrambler1(byte[] key) {
        byte[] scrambledKey = new byte[key.length];
        for (int i = 0; i < key.length; i++) {
            scrambledKey[i] = (byte) (key[i] ^ (key[(i + 9) % key.length] ^ key[(i + 3) % key.length]));
        }
        return scrambledKey;
    }

    private static byte[] applyScrambler2(byte[] key) {
        byte[] scrambledKey = new byte[key.length];
        for (int i = 0; i < key.length; i++) {
            scrambledKey[i] = (byte) (key[i] ^ (key[(i + 9) % key.length] ^ key[(i + 4) % key.length]));
        }
        return scrambledKey;
    }

    private static byte[] encryptOneTime(byte[] data, byte[] key) {
        byte[] encryptedData = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            encryptedData[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return encryptedData;
    }

    private static byte[] decryptOneTime(byte[] encryptedData, byte[] key) {
        return encryptOneTime(encryptedData, key);
    }

    public static void main(String[] args) {
        try {
            byte[] inputData = readFile("input.txt");

            byte[] oneTimeKey;

            boolean useRandomKey = true;
            if (useRandomKey) {
                oneTimeKey = generateKey(inputData.length);
            } else {
                oneTimeKey = generateKey(inputData.length);
                byte[] scrambledKey1 = applyScrambler1(oneTimeKey);
                byte[] scrambledKey2 = applyScrambler2(oneTimeKey);

                byte scramblerChoise = 1;
                switch (scramblerChoise){
                    case 1 -> {
                        for (int i = 0; i < oneTimeKey.length; i++) {
                            oneTimeKey[i] = scrambledKey1[i];
                        }}
                    case 2 -> {
                        for (int i = 0; i < oneTimeKey.length; i++) {
                            oneTimeKey[i] = scrambledKey2[i];
                        }
                    }
                }
            }

            byte[] encryptedData = encryptOneTime(inputData, oneTimeKey);

            writeFile("encrypted_data.txt", encryptedData);
            writeFile("encryption_key.txt", oneTimeKey);

            byte[] decryptionKey = readFile("encryption_key.txt");

            byte[] decryptedData = decryptOneTime(encryptedData, decryptionKey);

            writeFile("decrypted_data.txt", decryptedData);

            System.out.println("Done.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
