# Basic Encryption Standard

O Basic Encryption Standard (BES) é um algoritmo simples de criptografia baseado em uma matriz 4x4 e manipulação de bits. Este projeto visa implementar esse sistema de criptografia.

## Funcionalidades
+ Conversão de texto para binário
+ Separação de binário em matrizes 4x4
+ Troca entre colunas 1/2 e 3/4 de todas as matrizes e a chave
+ XOR entre bits das matrizes com a chave
+ Shift das linhas das matrizes, +0 +1 +2 +3 respectivamente

## Instalação

1. Clone o reposiório
```bash
git clone https://github.com/zezevitor/BES.git
```

2. Compile o código:
```bash
cd .\BES\src\
javac Main.java
```

3. Execute o programa:
```bash
java Main
```
