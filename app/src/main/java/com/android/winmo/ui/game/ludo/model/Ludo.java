package com.android.winmo.ui.game.ludo.model;

public class Ludo {
    String playerNo, diceNo, token1, token2, token3, token4, token5, token6, token7, token8, token9, token10, token11, token12, token13, token14, token15, token16,deviceId;
    int x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, extra;
    int player1,player2,player3, player0,gameStart;

    public Ludo() {
    }

    public int getGameStart() {
        return gameStart;
    }

    public int getPlayer1() {
        return player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public int getPlayer3() {
        return player3;
    }

    public int getPlayer0() {
        return player0;
    }

    public int getExtra() {
        return extra;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(String playerNo) {
        this.playerNo = playerNo;
    }

    public String getDiceNo() {
        return diceNo;
    }

    public void setDiceNo(String diceNo) {
        this.diceNo = diceNo;
    }

    public String getToken1() {
        return token1;
    }

    public void setToken1(String token1) {
        this.token1 = token1;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getToken3() {
        return token3;
    }

    public void setToken3(String token3) {
        this.token3 = token3;
    }

    public String getToken4() {
        return token4;
    }

    public void setToken4(String token4) {
        this.token4 = token4;
    }

    public String getToken5() {
        return token5;
    }

    public void setToken5(String token5) {
        this.token5 = token5;
    }

    public String getToken6() {
        return token6;
    }

    public void setToken6(String token6) {
        this.token6 = token6;
    }

    public String getToken7() {
        return token7;
    }

    public void setToken7(String token7) {
        this.token7 = token7;
    }

    public String getToken8() {
        return token8;
    }

    public void setToken8(String token8) {
        this.token8 = token8;
    }

    public String getToken9() {
        return token9;
    }

    public void setToken9(String token9) {
        this.token9 = token9;
    }

    public String getToken10() {
        return token10;
    }

    public void setToken10(String token10) {
        this.token10 = token10;
    }

    public String getToken11() {
        return token11;
    }

    public void setToken11(String token11) {
        this.token11 = token11;
    }

    public String getToken12() {
        return token12;
    }

    public void setToken12(String token12) {
        this.token12 = token12;
    }

    public String getToken13() {
        return token13;
    }

    public void setToken13(String token13) {
        this.token13 = token13;
    }

    public String getToken14() {
        return token14;
    }

    public void setToken14(String token14) {
        this.token14 = token14;
    }

    public String getToken15() {
        return token15;
    }

    public void setToken15(String token15) {
        this.token15 = token15;
    }

    public String getToken16() {
        return token16;
    }

    public void setToken16(String token16) {
        this.token16 = token16;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getX4() {
        return x4;
    }

    public void setX4(int x4) {
        this.x4 = x4;
    }

    public int getX5() {
        return x5;
    }

    public void setX5(int x5) {
        this.x5 = x5;
    }

    public int getX6() {
        return x6;
    }

    public void setX6(int x6) {
        this.x6 = x6;
    }

    public int getX7() {
        return x7;
    }

    public void setX7(int x7) {
        this.x7 = x7;
    }

    public int getX8() {
        return x8;
    }

    public void setX8(int x8) {
        this.x8 = x8;
    }

    public int getX9() {
        return x9;
    }

    public void setX9(int x9) {
        this.x9 = x9;
    }

    public int getX10() {
        return x10;
    }

    public void setX10(int x10) {
        this.x10 = x10;
    }

    public int getX11() {
        return x11;
    }

    public void setX11(int x11) {
        this.x11 = x11;
    }

    public int getX12() {
        return x12;
    }

    public void setX12(int x12) {
        this.x12 = x12;
    }

    public int getX13() {
        return x13;
    }

    public void setX13(int x13) {
        this.x13 = x13;
    }

    public int getX14() {
        return x14;
    }

    public int getX15() {
        return x15;
    }

    public void setX15(int x15) {
        this.x15 = x15;
    }

    public int getX16() {
        return x16;
    }

    public void setX16(int x16) {
        this.x16 = x16;
    }

    public Ludo(int gs,int x, String deviceId,String playerNo, String diceNo, String token1, String token2, String token3, String token4, String token5, String token6, String token7, String token8, String token9, String token10, String token11, String token12, String token13, String token14, String token15, String token16, int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, int x9, int x10, int x11, int x12, int x13, int x14, int x15, int x16,int p1,int p2,int p3,int p0) {
        this.gameStart = gs;
        this.extra = x;
        this.deviceId = deviceId;
        this.playerNo = playerNo;
        this.diceNo = diceNo;
        this.token1 = token1;
        this.token2 = token2;
        this.token3 = token3;
        this.token4 = token4;
        this.token5 = token5;
        this.token6 = token6;
        this.token7 = token7;
        this.token8 = token8;
        this.token9 = token9;
        this.token10 = token10;
        this.token11 = token11;
        this.token12 = token12;
        this.token13 = token13;
        this.token14 = token14;
        this.token15 = token15;
        this.token16 = token16;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
        this.x6 = x6;
        this.x7 = x7;
        this.x8 = x8;
        this.x9 = x9;
        this.x10 = x10;
        this.x11 = x11;
        this.x12 = x12;
        this.x13 = x13;
        this.x14 = x14;
        this.x15 = x15;
        this.x16 = x16;
        this.player1 = p1;
        this.player2 = p2;
        this.player3 = p3;
        this.player0 = p0;
    }
}
