package com.banco;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    ContaCorrente[] contas = new ContaCorrente[15];

    public static void main( String[] args )
    {
        App app = new App();
        app.criarContas();
        app.exibirMenu();
    }

    private void criarContas() {
        for (int i = 0; i < 15; i++) {
            contas[i] = new ContaCorrente(i, i+1000.50);
        }
    }

    private void exibirMenu() {
        System.out.println("\n1.Sacar\n2.Depositar\n3.Consultar Saldo\n4.Listar contas\n5.Sair\n");
        System.out.println("Digite a opção desejada: ");
        Scanner scan = new Scanner(System.in);
        try {
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    sacar();
                    break;
                case 2:
                    depositar();
                    break;
                case 3:
                    consultarSaldo();
                    break;
                case 4:
                    listarContas();
                case 5:
                    System.exit(0);
            
                default:
                    System.out.println("Opção inválida!!\n");
                    exibirMenu();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Opção inválida!!\n");
                exibirMenu();
        }
    }

    private void sacar() {
        System.out.println("Digite a conta para saque: ");
        Scanner scan = new Scanner(System.in);
        int numeroConta = scan.nextInt();
        if (pesquisaConta(numeroConta) != null) {
            ContaCorrente contaSaque = pesquisaConta(numeroConta);
            System.out.println("\nQual o valor a ser sacado: ");
            double valor = scan.nextDouble();
            if (contaSaque.getSaldo() < valor) {
                System.out.println("Saldo insuficiente\n");
                exibirMenu();
            } else {
                contas[contaSaque.getNumero()].setSaldo(contaSaque.getSaldo() - valor);
                System.out.println("Saque efetuado com sucesso!!\nNovo saldo: " + contas[contaSaque.getNumero()].getSaldo() + "\n");
                exibirMenu();
            }
        } else {
            exibirMenu();
        }
    }

    private ContaCorrente pesquisaConta(int numeroConta) {
        for (ContaCorrente conta : contas) {
            if (conta.getNumero() == numeroConta) {
                ContaCorrente contaEncontrada = conta;
                return contaEncontrada;
            }
        }
        System.out.println("Conta não cadastrada!\n");
        return null;
    }

    private void depositar() {
        System.out.println("Digite a conta para depósito: ");
        Scanner scan = new Scanner(System.in);
        int numeroConta = scan.nextInt();
        if (pesquisaConta(numeroConta) != null) {
            ContaCorrente contaDeposito = pesquisaConta(numeroConta);
            System.out.println("\nQual o valor a ser depositado: ");
            double valor = scan.nextDouble();
            contas[contaDeposito.getNumero()].setSaldo(contaDeposito.getSaldo() + valor);
            System.out.println("Depósito efetuado com sucesso!!\nNovo saldo: " + contas[contaDeposito.getNumero()].getSaldo() + "\n");
            exibirMenu();
        } else {
            exibirMenu();
        }
    }

    private void consultarSaldo() {
        System.out.println("Digite a conta para saldo: ");
        Scanner scan = new Scanner(System.in);
        int numeroConta = scan.nextInt();
        if(pesquisaConta(numeroConta) != null) {
            ContaCorrente contaSaldo = pesquisaConta(numeroConta);
            System.out.println("\nSaldo: " + contaSaldo.getSaldo() + "\n");
            exibirMenu();
        } else {
            exibirMenu();
        }
    }

    private void listarContas() {
        System.out.println("\nNúmero da conta\t\tSaldo");
        for (ContaCorrente contaCorrente : contas) {
            System.out.println(contaCorrente.getNumero() + "\t\t\t" + contaCorrente.getSaldo());
        }
        exibirMenu();
    }
}
