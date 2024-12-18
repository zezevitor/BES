import java.util.Random;

public class BasicEncryptionStandard {
    // Converte uma string para binário
    public String stringToBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder bin = new StringBuilder();

        for (byte b : bytes) {
           int val = b;
           for (int i = 0; i < 8; i++) {
              bin.append((val & 128) == 0 ? 0 : 1);
              val <<= 1;
           }
        }
        
        return bin.toString();
    };

    // Gera uma chave aleatória
    public int[][] generateKey() {
        int[][] key = new int[4][4];
        Random r = new Random();

        System.out.println("\nChave: ");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                key[i][j] = r.nextInt(2);
                System.out.print(key[i][j] + " ");
            }
            
            System.out.print("\n");
        }

        return key;
    }

    // Criptograva uma string binária a partir de uma chave binária 4x4
    public String encrypt(String bin, int[][] key) {
        int[][] copyKey = new int[4][4];
        if (key == null || key.length != 4 || key[0].length != 4) {
            throw new IllegalArgumentException("Key must be a 4x4 matrix.");
        } else {
            for (int i = 0; i < 4; i++) {
                System.arraycopy(key[i], 0, copyKey[i], 0, 4);
            }
        }

        int[][][] textBlocks = createBlocks(bin);
        
        shiftRows(textBlocks);
        
        xor(textBlocks, copyKey);
        
        swapColumns(textBlocks);

        return blocksToString(textBlocks);
    };

    // Descriptograva uma string binária a partir de uma chave binária 4x4
    public String decrypt(String bin, int[][] key) {
        int[][] copyKey = new int[4][4];
        if (key == null || key.length != 4 || key[0].length != 4) {
            throw new IllegalArgumentException("Key must be a 4x4 matrix.");
        } else {
            for (int i = 0; i < 4; i++) {
                System.arraycopy(key[i], 0, copyKey[i], 0, 4);
            }
        }

        int[][][] textBlocks = createBlocks(bin);
        
        swapColumns(textBlocks);
        
        xor(textBlocks, copyKey);
        
        reverseShiftRows(textBlocks);

        return blocksToString(textBlocks);
    };

    // Desvolve as matrizes 4x4 recebidas
    private void printBlocks(int[][][] textBlocks) {
        for (int matrix = 0; matrix < textBlocks.length; matrix++) {
            for (int i = 0; i < 4; i++) {
                System.out.println(textBlocks[matrix][i][0] + " " + textBlocks[matrix][i][1] + " " + textBlocks[matrix][i][2] + " " + textBlocks[matrix][i][3] + " ");
            }
            System.out.println();
        }
    };

    // Organiza uma String binária em blocos de matrizes 4x4
    private int[][][] createBlocks(String bin) {
        int fullMatrix = bin.length() / 16;
        int partialMatrix = bin.length() % 16 == 0 ? 0 : 1;
        int totalBlocks = fullMatrix + partialMatrix;

        int[][][] textBlocks = new int[totalBlocks][4][4];
        int charIndex = 0;

        for (int matrix = 0; matrix < fullMatrix; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    textBlocks[matrix][i][j] = bin.charAt(charIndex) - 48;
                    charIndex++;
                }
            }
        }

        if (partialMatrix != 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    textBlocks[totalBlocks - 1][i][j] = charIndex < bin.length() ? bin.charAt(charIndex) - 48 : 0;
                    charIndex++;
                }
            }
        }

        return textBlocks;
    };

    // Troca as colunas 1/2 e 3/4
    private void swapColumns(int[][][] textBlocks) {
        for (int i = 0; i < 4; i++) {
            for (int matrix = 0; matrix < textBlocks.length; matrix++) {
                int temp = textBlocks[matrix][i][0];
                textBlocks[matrix][i][0] = textBlocks[matrix][i][1];
                textBlocks[matrix][i][1] = temp;

                temp = textBlocks[matrix][i][2];
                textBlocks[matrix][i][2] = textBlocks[matrix][i][3];
                textBlocks[matrix][i][3] = temp;                
            }
        }
    };

    // Aplica XOR em todas as matrizes usando a chave
    private void xor(int[][][] textBlocks, int[][] copyKey) {
        for (int matrix = 0; matrix < textBlocks.length; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    textBlocks[matrix][i][j] = textBlocks[matrix][i][j] ^ copyKey[i][j];
                }
            }
        }
    };

    // Move cada coluna das matrizes em -0, -1, -2, -3
    private void shiftRows(int[][][] textBlocks) {
        for (int matrix = 0; matrix < textBlocks.length; matrix++) {
            for (int i = 0; i < 4; i++) {
                int shiftCount = i;
                for (int count = 0; count < shiftCount; count++) {
                    int temp = textBlocks[matrix][i][0];
                    for (int j = 0; j < 3; j++) {
                        textBlocks[matrix][i][j] = textBlocks[matrix][i][j + 1];
                    }
                    textBlocks[matrix][i][3] = temp;
                }
            }
        }    
    };
    
    // Move cada coluna das matrizes em +0, +1, +2, +3
    private void reverseShiftRows(int[][][] textBlocks) {
        for (int matrix = 0; matrix < textBlocks.length; matrix++) {
            for (int i = 0; i < 4; i++) {
                int shiftCount = i;
                for (int count = 0; count < shiftCount; count++) {
                    int temp = textBlocks[matrix][i][3];
                    for (int j = 3; j > 0; j--) {
                        textBlocks[matrix][i][j] = textBlocks[matrix][i][j - 1];
                    }
                    textBlocks[matrix][i][0] = temp;
                }
            }
        }            
    };

    // Converte os blocos de matrizes de volta para uma string
    private String blocksToString(int[][][] textBlocks) {
        String encrypted = "";
        for (int matrix = 0; matrix < textBlocks.length; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    encrypted += textBlocks[matrix][i][j];
                }
            }
        }
        return encrypted;
    };

}