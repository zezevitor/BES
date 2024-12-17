public class BasicEncryptionStandard {
    // Função para traduzir uma String para binário
    public String stringToBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder bin = new StringBuilder();

        for (byte b : bytes)
        {
           int val = b;
           for (int i = 0; i < 8; i++)
           {
              bin.append((val & 128) == 0 ? 0 : 1);
              val <<= 1;
           }
        }
        
        return bin.toString();
    }

    // Função de 
    public String encrypt(String bin, int[][] key) {
        String encrypted = "";
        int[][] copyKey = new int[4][4];
        if (key == null || key.length != 4 || key[0].length != 4) {
            throw new IllegalArgumentException("Key must be a 4x4 matrix.");
        } else {
            for (int i = 0; i < 4; i++) {
                System.arraycopy(key[i], 0, copyKey[i], 0, 4);
            }
        }

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
        
        for (int i = 0; i < 4; i++) {
            for (int matrix = 0; matrix < totalBlocks; matrix++) {
                int temp = textBlocks[matrix][i][0];
                textBlocks[matrix][i][0] = textBlocks[matrix][i][1];
                textBlocks[matrix][i][1] = temp;

                temp = textBlocks[matrix][i][2];
                textBlocks[matrix][i][2] = textBlocks[matrix][i][3];
                textBlocks[matrix][i][3] = temp;                
            }

            int temp = copyKey[i][0];
            copyKey[i][0] = copyKey[i][1];
            copyKey[i][1] = temp;
    
            temp = copyKey[i][2];
            copyKey[i][2] = copyKey[i][3];
            copyKey[i][3] = temp;
        }
        
        for (int matrix = 0; matrix < totalBlocks; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    textBlocks[matrix][i][j] = textBlocks[matrix][i][j] ^ copyKey[i][j];
                }
            }
        }

        for (int matrix = 0; matrix < totalBlocks; matrix++) {
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

        for (int matrix = 0; matrix < totalBlocks; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    encrypted += textBlocks[matrix][i][j];
                }
            }
        }

        return encrypted;
    }

    //Funçã de
    public String decrypt(String bin, int[][] key) {
        String result = "";
        int[][] copyKey = new int[4][4];
        if (key == null || key.length != 4 || key[0].length != 4) {
            throw new IllegalArgumentException("Key must be a 4x4 matrix.");
        } else {
            for (int i = 0; i < 4; i++) {
                System.arraycopy(key[i], 0, copyKey[i], 0, 4);
            }
        }

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

        for (int i = 0; i < 4; i++) {
            for (int matrix = 0; matrix < totalBlocks; matrix++) {
                int temp = textBlocks[matrix][i][0];
                textBlocks[matrix][i][0] = textBlocks[matrix][i][1];
                textBlocks[matrix][i][1] = temp;

                temp = textBlocks[matrix][i][2];
                textBlocks[matrix][i][2] = textBlocks[matrix][i][3];
                textBlocks[matrix][i][3] = temp;                
            }

            int temp = copyKey[i][0];
            copyKey[i][0] = copyKey[i][1];
            copyKey[i][1] = temp;
    
            temp = copyKey[i][2];
            copyKey[i][2] = copyKey[i][3];
            copyKey[i][3] = temp;
        }
        
        for (int matrix = 0; matrix < totalBlocks; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    textBlocks[matrix][i][j] = textBlocks[matrix][i][j] ^ copyKey[i][j];
                }
            }
        }

        for (int matrix = 0; matrix < totalBlocks; matrix++) {
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

        for (int matrix = 0; matrix < totalBlocks; matrix++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    result += textBlocks[matrix][i][j];
                }
            }
        }

        return result;
    }

    // FAZER METODOS AUXILIARES PARA TESTAR O ERRO

}