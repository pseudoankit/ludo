package com.android.winmo.ui.game.ludo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.winmo.R;
import com.android.winmo.ui.game.result.GameResultActivity;
import com.android.winmo.ui.game.ludo.model.Ludo;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

import static android.content.ContentValues.TAG;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.DEVICE_ID;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.DICE_NO;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.EXTRA;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.FB_LUDO;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.PLAYER_NO;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.ROOM_ID;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.SELECTED_PLAYER;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN1;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN10;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN11;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN12;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN13;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN14;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN15;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN16;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN2;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN3;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN4;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN5;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN6;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN7;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN8;
import static com.android.winmo.ui.game.ludo.constant.LudoConstants.TOKEN9;

public class LudoMultiPlayerActivity extends Activity implements View.OnClickListener {

    public static String WINNER = "winner";
    private static final int autoRunToken = 5000;
    private final int cd = 50;
    private static final int updatePlayerNo = 700;
    private static final int mapDiceUi = 700;
    private int changeDicePosi = 900;

    public int height, width, top, bottom, d, n, playerNo;
    public int x1 = 1, x2 = 1, x3 = 1, x4 = 1, x5 = 14, x6 = 14, x7 = 14, x8 = 14, x9 = 27, x10 = 27, x11 = 27, x12 = 27, x13 = 40, x14 = 40, x15 = 40, x16 = 40;
    public ImageView dice, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16;
    private MaterialCardView mcv;
    public int n1, n2, n3, n4;
    public int extraN;
    public int p11, p12, p13, p14 = 0, p21, p22, p23, p24 = 0, p31, p32, p33, p34 = 0, p41, p42, p43, p44 = 0;
    private ImageView c1, c2, c3, c4, c5, c6, c7, c8;
    public int r, b, g, y = 0;
    private int diceSize = 0;
    private ImageView in1, in2, in3, in4;
    private boolean iv1h, iv2h, iv3h, iv4h, iv5h, iv6h, iv7h, iv8h, iv9h, iv10h, iv11h, iv12h, iv13h, iv14h, iv15h, iv16h;
    private int pt;
    private RelativeLayout rollDice;
    private GifImageView animDice;
    private ProgressBar pbTimer;
    private CountDownTimer cdTimer1, cdTimer2, cdTimer3, cdTimer4, cdTimer;
    private int t = 0;
    private int selectedPlayer;
    private Ludo ludo;
    private DatabaseReference dbRef;
    private String deviceId, roomKey;
    private boolean isDiceRolled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_mullti_player);

        getSelectedPlayer();
//        initFireBase();
        dbRef = FirebaseDatabase.getInstance().getReference().child(FB_LUDO).child(roomKey);
        ludo = new Ludo();
        assign();
        try {
            setDicePosi(5, top + width + 25);
        } catch (Exception ignored) {
        }
        fill1(iv1);
        fill1(iv2);
        fill1(iv3);
        fill1(iv4);
        fill2(iv5);
        fill2(iv6);
        fill2(iv7);
        fill2(iv8);
        fill3(iv9);
        fill3(iv10);
        fill3(iv11);
        fill3(iv12);
        fill4(iv13);
        fill4(iv14);
        fill4(iv15);
        fill4(iv16);
        fillCheckPost(c1);
        fillCheckPost(c2);
        fillCheckPost(c3);
        fillCheckPost(c4);
        fillCheckPost(c5);
        fillCheckPost(c6);
        fillCheckPost(c7);
        fillCheckPost(c8);
        startGame();

        fbListener();
    }

    private void getSelectedPlayer() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            selectedPlayer = b.getInt(SELECTED_PLAYER);
            roomKey = b.getString(ROOM_ID);
            deviceId = b.getString(DEVICE_ID);
            Log.d(TAG, "getSelectedPlayer: " + roomKey + "," + selectedPlayer + "," + deviceId);
        }
    }

    private void updateTokenFb(String keyT, String valueT) {
        if (ludo == null && !ludo.getDeviceId().equals(deviceId)) return;
        HashMap<String, Object> map = new HashMap<>();
        map.put(keyT, valueT);
        dbRef.updateChildren(map);
    }

    private void updateXFb(String keyX, int valueX) {
        if (ludo == null && !ludo.getDeviceId().equals(deviceId)) return;
        HashMap<String, Object> map = new HashMap<>();
        map.put(keyX, valueX);
        dbRef.updateChildren(map);
    }

    private void updatePlayer() {
        pbTimer.setVisibility(View.VISIBLE);
        isDiceRolled = false;
        t = 0;
        cdTimer = new CountDownTimer(10000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer.setProgress(t);
                t++;
            }

            @Override
            public void onFinish() {
                if (!isDiceRolled) {
                    pbTimer.setVisibility(View.GONE);
                    playerNo++;
                    updateDicePosition();
                }
            }
        }.start();
    }

    private void updatePlayerFb() {
        if (ludo == null && !ludo.getDeviceId().equals(deviceId)) return;
        HashMap<String, Object> map = new HashMap<>();
        map.put(PLAYER_NO, String.valueOf(playerNo));
        dbRef.updateChildren(map);
    }

    private void fbListener() {
        dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                ludo = dataSnapshot.getValue(Ludo.class);
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                Object value = snapshot.getValue();
                Log.d(TAG, "onChildChanged: " + key + "," + value);
                assert key != null;
                assert value != null;
                if (!ludo.getDeviceId().equals(deviceId)) {
//                    playerNo = Integer.parseInt(ludo.getPlayerNo());
//                    updateDicePosition();
                    if (key.equals(DICE_NO)) {
                        n = Integer.parseInt(String.valueOf(value));
                        mapDiceToUi(n);
                    }
                    updateUi(key, value);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUi(String key, Object value) {

        switch (key) {
            case PLAYER_NO:
                playerNo = Integer.parseInt(String.valueOf(value));
                updateDicePosition();
                break;
            case TOKEN1:
                if ("1out".equals(value)) {
                    fill1(iv1);
                } else if ("1i".equals(value)) {
                    p11 = 0;
                    token1(iv1);
                } else if ("1home".equals(value)) {
                    n1 = 6;
                    homeToken1();
                } else {
                    x1 = ludo.getX1();
                    token1(iv1);
                }
                break;
            case TOKEN2:
                if ("2out".equals(value)) {
                    fill1(iv2);
                } else if ("2i".equals(value)) {
                    p12 = 0;
                    token2(iv2);
                } else if ("2home".equals(value)) {
                    n2 = 6;
                    homeToken2();
                } else {
                    x2 = ludo.getX2();
                    token2(iv2);
                }
                break;
            case TOKEN3:
                if ("3i".equals(value)) {
                    p13 = 0;
                    token3(iv3);
                } else if ("3home".equals(value)) {
                    n3 = 6;
                    homeToken3();
                } else if ("3out".equals(value)) {
                    fill1(iv3);
                } else {
                    x3 = ludo.getX3();
                    token3(iv3);
                }
                break;
            case TOKEN4:
                if ("4i".equals(value)) {
                    p14 = 0;
                    token4(iv4);
                } else if ("4home".equals(value)) {
                    n4 = 6;
                    homeToken4();
                } else if ("4out".equals(value)) {
                    fill1(iv4);
                } else {
                    x4 = ludo.getX4();
                    token4(iv4);
                }
                break;
            case TOKEN5:
                if ("5i".equals(value)) {
                    p31 = 0;
                    token5(iv5);
                } else if ("5home".equals(value)) {
                    n1 = 6;
                    homeToken5();
                } else if ("5out".equals(value)) {
                    fill2(iv5);
                } else {
                    x5 = ludo.getX5();
                    token5(iv5);
                }
                break;
            case TOKEN6:
                if ("6i".equals(value)) {
                    p32 = 0;
                    token6(iv6);
                } else if ("6home".equals(value)) {
                    n2 = 6;
                    homeToken6();
                } else if ("6out".equals(value)) {
                    fill2(iv6);
                } else {
                    x6 = ludo.getX6();
                    token6(iv6);
                }
                break;
            case TOKEN7:
                if ("7i".equals(value)) {
                    p33 = 0;
                    token7(iv7);
                } else if ("7home".equals(value)) {
                    n3 = 6;
                    homeToken7();
                } else if ("7out".equals(value)) {
                    fill2(iv7);
                } else {
                    x7 = ludo.getX7();
                    token7(iv7);
                }
                break;
            case TOKEN8:
                if ("8i".equals(value)) {
                    p34 = 0;
                    token8(iv8);
                } else if ("8home".equals(value)) {
                    n4 = 6;
                    homeToken8();
                } else if ("8out".equals(value)) {
                    fill2(iv8);
                } else {
                    x8 = ludo.getX8();
                    token8(iv8);
                }
                break;
            case TOKEN9:
                if ("9i".equals(value)) {
                    p21 = 0;
                    token9(iv9);
                } else if ("9home".equals(value)) {
                    n1 = 6;
                    homeToken9();
                } else if ("9out".equals(value)) {
                    fill3(iv9);
                } else {
                    x9 = ludo.getX9();
                    token9(iv9);
                }
                break;
            case TOKEN10:
                if ("10i".equals(value)) {
                    p22 = 0;
                    token10(iv10);
                } else if ("10home".equals(value)) {
                    n2 = 6;
                    homeToken10();
                } else if ("10out".equals(value)) {
                    fill3(iv10);
                } else {
                    x10 = ludo.getX10();
                    token10(iv10);
                }
                break;
            case TOKEN11:
                if ("11i".equals(value)) {
                    p23 = 0;
                    token11(iv11);
                } else if ("11home".equals(value)) {
                    n3 = 6;
                    homeToken11();
                } else if ("11out".equals(value)) {
                    fill3(iv11);
                } else {
                    x11 = ludo.getX11();
                    token11(iv11);
                }
                break;
            case TOKEN12:
                if ("12i".equals(value)) {
                    p24 = 0;
                    token12(iv12);
                } else if ("12home".equals(value)) {
                    n4 = 6;
                    homeToken12();
                } else if ("12out".equals(value)) {
                    fill3(iv12);
                } else {
                    x12 = ludo.getX12();
                    token12(iv12);
                }
                break;
            case TOKEN13:
                if ("13i".equals(value)) {
                    p41 = 0;
                    token13(iv13);
                } else if ("13home".equals(value)) {
                    n1 = 6;
                    homeToken13();
                } else if ("13out".equals(value)) {
                    fill4(iv13);
                } else {
                    x13 = ludo.getX13();
                    token13(iv13);
                }
                break;
            case TOKEN14:
                if ("14i".equals(value)) {
                    p42 = 0;
                    token14(iv14);
                } else if ("14home".equals(value)) {
                    n2 = 6;
                    homeToken14();
                } else if ("14out".equals(value)) {
                    fill4(iv14);
                } else {
                    x14 = ludo.getX14();
                    token14(iv14);
                }
                break;
            case TOKEN15:
                if ("15i".equals(value)) {
                    p43 = 0;
                    token15(iv15);
                } else if ("15home".equals(value)) {
                    n3 = 6;
                    homeToken15();
                } else if ("15out".equals(value)) {
                    fill4(iv15);
                } else {
                    x15 = ludo.getX15();
                    token15(iv15);
                }
                break;
            case TOKEN16:
                if ("16i".equals(value)) {
                    p44 = 0;
                    token16(iv16);
                } else if ("16home".equals(value)) {
                    n4 = 6;
                    homeToken16();
                } else if ("16out".equals(value)) {
                    fill4(iv16);
                } else {
                    x16 = ludo.getX16();
                    token16(iv16);
                }
                break;
        }
    }

    private void token1(View v) {

        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p11 == 0) {
            mP.leftMargin = d;
            mP.topMargin = top + 6 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p11 = 1;
            updateTokenFb(TOKEN1, "1i");
        } else x1 = positionOf(x1, iv1);
        checkPosition(iv1);
        homeToken1();
        updateXFb("x1", x1);
        if (cdTimer1 != null) cdTimer1.cancel();
    }

    private void homeToken1() {
        if (n1 == 6) {
            iv1.setVisibility(View.INVISIBLE);
            iv1.setClickable(false);
            iv1h = true;
            x1 = 0;
            updateTokenFb(TOKEN1, "1home");
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token2(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p12 == 0) {
            mP.leftMargin = d;
            mP.topMargin = top + 6 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p12 = 1;
            updateTokenFb(TOKEN2, "2i");
        } else x2 = positionOf(x2, iv2);
        checkPosition(iv2);
        homeToken2();
        updateXFb("x2", x2);
        if (cdTimer1 != null) cdTimer1.cancel();
    }

    private void homeToken2() {
        if (n2 == 6) {
            iv2.setVisibility(View.INVISIBLE);
            iv2.setClickable(false);
            updateTokenFb(TOKEN2, "2home");
            iv2h = true;
            x2 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token3(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p13 == 0) {
            mP.leftMargin = d;
            mP.topMargin = top + 6 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p13 = 1;
            updateTokenFb(TOKEN3, "3i");
        } else x3 = positionOf(x3, iv3);
        checkPosition(iv4);
        homeToken3();
        updateXFb("x3", x3);
        if (cdTimer1 != null) cdTimer1.cancel();
    }

    private void homeToken3() {
        if (n3 == 6) {
            iv3.setVisibility(View.INVISIBLE);
            iv3.setClickable(false);
            updateTokenFb(TOKEN3, "3home");
            iv3h = true;
            x3 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token4(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();

        if (p14 == 0) {
            mP.leftMargin = d;
            mP.topMargin = top + 6 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p14 = 1;
            updateTokenFb(TOKEN4, "4i");
        } else x4 = positionOf(x4, iv4);
        checkPosition(iv4);
        homeToken4();
        updateXFb("x4", x4);
        if (cdTimer1 != null) cdTimer1.cancel();
    }

    private void homeToken4() {
        if (n4 == 6) {
            iv4.setVisibility(View.INVISIBLE);
            iv4.setClickable(false);
            updateTokenFb(TOKEN4, "4home");
            iv4h = true;
            x4 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token5(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p31 == 0) {
            mP.leftMargin = 8 * d;
            mP.topMargin = top + d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p31 = 1;
            updateTokenFb(TOKEN5, "5i");
        } else x5 = positionOf(x5, iv5);
        checkPosition(iv5);
        homeToken5();
        updateXFb("x5", x5);
        if (cdTimer2 != null) cdTimer2.cancel();
    }

    private void homeToken5() {
        if (n1 == 6 && iv5.getTop() < top + 7 * d && iv5.getLeft() == 7 * d) {
            iv5.setVisibility(View.INVISIBLE);
            iv5.setClickable(false);
            updateTokenFb(TOKEN5, "5home");
            iv5h = true;
            x5 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token6(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p32 == 0) {
            mP.leftMargin = 8 * d;
            mP.topMargin = top + d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p32 = 1;
            updateTokenFb(TOKEN6, "6i");
        } else x6 = positionOf(x6, iv6);
        checkPosition(iv6);
        homeToken6();
        updateXFb("x6", x6);
        if (cdTimer2 != null) cdTimer2.cancel();
    }

    private void homeToken6() {
        if (n2 == 6 && iv6.getTop() < top + 7 * d && iv6.getLeft() == 7 * d) {
            iv6.setVisibility(View.INVISIBLE);
            iv6.setClickable(false);
            updateTokenFb(TOKEN6, "6home");
            iv6h = true;
            x6 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token7(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p33 == 0) {
            mP.leftMargin = 8 * d;
            mP.topMargin = top + d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p33 = 1;
            updateTokenFb(TOKEN7, "7i");
        } else x7 = positionOf(x7, iv7);
        checkPosition(iv7);
        homeToken7();
        updateXFb("x7", x7);
        if (cdTimer2 != null) cdTimer2.cancel();
    }

    private void homeToken7() {
        if (n3 == 6 && iv7.getTop() < top + 7 * d && iv7.getLeft() == 7 * d) {
            iv7.setVisibility(View.INVISIBLE);
            iv7.setClickable(false);
            updateTokenFb(TOKEN7, "7home");
            iv7h = true;
            x7 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token8(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p34 == 0) {
            mP.leftMargin = 8 * d;
            mP.topMargin = top + d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p34 = 1;
            updateTokenFb(TOKEN8, "8i");
        } else x8 = positionOf(x8, iv8);
        checkPosition(iv8);
        homeToken8();
        updateXFb("x8", x8);
        if (cdTimer2 != null) cdTimer2.cancel();
    }

    private void homeToken8() {
        if (n4 == 6 && iv8.getTop() < top + 7 * d && iv8.getLeft() == 7 * d) {
            iv8.setVisibility(View.INVISIBLE);
            iv8.setClickable(false);
            updateTokenFb(TOKEN8, "8home");
            iv8h = true;
            x8 = 0;
            setDiceClickable();
            playerNo = pt;
        }
    }

    private void token9(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p21 == 0) {
            mP.leftMargin = 13 * d;
            mP.topMargin = top + 8 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p21 = 1;
            updateTokenFb(TOKEN9, "9i");
        } else x9 = positionOf(x9, iv9);
        checkPosition(iv9);
        homeToken9();
        updateXFb("x9", x9);
        if (cdTimer3 != null) cdTimer3.cancel();
    }

    private void homeToken9() {
        if (n1 == 6 && iv9.getLeft() > 7 * d && iv9.getTop() == top + 7 * d) {
            iv9.setVisibility(View.INVISIBLE);
            iv9.setClickable(false);
            updateTokenFb(TOKEN9, "9home");
            iv9h = true;
            playerNo = pt;
            x9 = 0;
            setDiceClickable();
        }
    }

    private void token10(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p22 == 0) {
            mP.leftMargin = 13 * d;
            mP.topMargin = top + 8 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p22 = 1;
            updateTokenFb(TOKEN10, "10i");
        } else x10 = positionOf(x10, iv10);
        checkPosition(iv10);
        homeToken10();
        updateXFb("x10", x10);
        if (cdTimer3 != null) cdTimer3.cancel();
    }

    private void homeToken10() {
        if (n2 == 6 && iv10.getLeft() > 7 * d && iv10.getTop() == top + 7 * d) {
            iv10.setVisibility(View.INVISIBLE);
            iv10.setClickable(false);
            updateTokenFb(TOKEN10, "10home");
            iv10h = true;
            x10 = 0;
            playerNo = pt;
            setDiceClickable();
        }
    }

    private void token11(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p23 == 0) {
            mP.leftMargin = 13 * d;
            mP.topMargin = top + 8 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p23 = 1;
            updateTokenFb(TOKEN11, "11i");
        } else x11 = positionOf(x11, iv11);
        checkPosition(iv11);
        homeToken11();
        updateXFb("x11", x11);
        if (cdTimer3 != null) cdTimer3.cancel();
    }

    private void homeToken11() {
        if (n3 == 6 && iv11.getLeft() > 7 * d && iv11.getTop() == top + 7 * d) {
            iv11.setVisibility(View.INVISIBLE);
            iv11.setClickable(false);
            updateTokenFb(TOKEN11, "11home");
            iv11h = true;
            playerNo = pt;
            x11 = 0;
            setDiceClickable();
        }
    }

    private void token12(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p24 == 0) {
            mP.leftMargin = 13 * d;
            mP.topMargin = top + 8 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p24 = 1;
            updateTokenFb(TOKEN12, "12i");
        } else x12 = positionOf(x12, iv12);
        checkPosition(iv12);
        homeToken12();
        updateXFb("x12", x12);
        if (cdTimer3 != null) cdTimer3.cancel();
    }

    private void homeToken12() {
        if (n4 == 6 && iv12.getLeft() > 7 * d && iv12.getTop() == top + 7 * d) {
            iv12.setVisibility(View.INVISIBLE);
            iv12.setClickable(false);
            updateTokenFb(TOKEN12, "12home");
            iv12h = true;
            playerNo = pt;
            x12 = 0;
            setDiceClickable();
        }
    }

    private void token13(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p41 == 0) {
            mP.leftMargin = 6 * d;
            mP.topMargin = top + 13 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p41 = 1;
            updateTokenFb(TOKEN13, "13i");
        } else x13 = positionOf(x13, iv13);
        checkPosition(iv13);
        homeToken13();
        updateXFb("x13", x13);
        if (cdTimer4 != null) cdTimer4.cancel();
    }

    private void homeToken13() {
        if (n1 == 6 && iv13.getTop() > top + 6 * d && iv13.getLeft() == 7 * d) {
            iv13.setVisibility(View.INVISIBLE);
            iv13.setClickable(false);
            updateTokenFb(TOKEN13, "13home");
            iv13h = true;
            playerNo = pt;
            x13 = 0;
            setDiceClickable();
        }
    }

    private void token14(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p42 == 0) {
            mP.leftMargin = 6 * d;
            mP.topMargin = top + 13 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p42 = 1;
            updateTokenFb(TOKEN14, "14i");
        } else x14 = positionOf(x14, iv14);
        checkPosition(iv14);
        homeToken14();
        updateXFb("x14", x14);
        if (cdTimer4 != null) cdTimer4.cancel();
    }

    private void homeToken14() {
        if (n2 == 6 && iv14.getTop() > top + 6 * d && iv14.getLeft() == 7 * d) {
            iv14.setVisibility(View.INVISIBLE);
            iv14.setClickable(false);
            updateTokenFb(TOKEN14, "14home");
            iv14h = true;
            playerNo = pt;
            x14 = 0;
            setDiceClickable();
        }
    }

    private void token15(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p43 == 0) {
            mP.leftMargin = 6 * d;
            mP.topMargin = top + 13 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p43 = 1;
            updateTokenFb(TOKEN15, "15i");
        } else x15 = positionOf(x15, iv15);
        checkPosition(iv15);
        homeToken15();
        updateXFb("x15", x15);
        if (cdTimer4 != null) cdTimer4.cancel();
    }

    private void homeToken15() {
        if (n3 == 6 && iv15.getTop() > top + 6 * d && iv15.getLeft() == 7 * d) {
            iv15.setVisibility(View.INVISIBLE);
            iv15.setClickable(false);
            updateTokenFb(TOKEN15, "15home");
            iv15h = true;
            playerNo = pt;
            x15 = 0;
            setDiceClickable();
        }
    }

    private void token16(View v) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        if (p44 == 0) {
            mP.leftMargin = 6 * d;
            mP.topMargin = top + 13 * d;
            v.setLayoutParams(mP);
            setDiceClickable();
            p44 = 1;
            updateTokenFb(TOKEN16, "16i");
        } else x16 = positionOf(x16, iv16);
        checkPosition(iv16);
        homeToken16();
        updateXFb("x16", x16);
        if (cdTimer4 != null) cdTimer4.cancel();
    }

    private void homeToken16() {
        if (n4 == 6 && iv16.getTop() > top + 6 * d && iv16.getLeft() == 7 * d) {
            iv16.setVisibility(View.INVISIBLE);
            iv16.setClickable(false);
            updateTokenFb(TOKEN16, "16home");
            iv16h = true;
            playerNo = pt;
            x16 = 0;
            setDiceClickable();
        }
    }

    @Override
    public void onClick(View v) {
        if (Integer.parseInt(ludo.getPlayerNo()) % 4 != selectedPlayer) return;
        HashMap<String, Object> map = new HashMap<>();
        map.put(DEVICE_ID, deviceId);
        dbRef.updateChildren(map);

        v.bringToFront();
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        switch (v.getId()) {

            case R.id.t1:
                token1(v);
                break;

            case R.id.t2:
                token2(v);
                break;

            case R.id.t3:
                token3(v);
                break;

            case R.id.t4:
                token4(v);
                break;

            case R.id.t5:
                token5(v);
                break;

            case R.id.t6:
                token6(v);
                break;

            case R.id.t7:
                token7(v);
                break;

            case R.id.t8:
                token8(v);
                break;

            case R.id.t9:
                token9(v);
                break;

            case R.id.t10:
                token10(v);
                break;

            case R.id.t11:
                token11(v);
                break;

            case R.id.t12:
                token12(v);
                break;

            case R.id.t13:
                token13(v);
                break;

            case R.id.t14:
                token14(v);
                break;

            case R.id.t15:
                token15(v);
                break;

            case R.id.t16:
                token16(v);
                break;

            case R.id.roll:
                Log.d(TAG, "onClick: " + playerNo);
                n = generateRandom();
                onDiceClick();
                break;
        }
        gameover();
    }

    private void onDiceClick() {
        isDiceRolled = true;
        cdTimer.cancel();
        animDice.setVisibility(View.VISIBLE);
        dice.setVisibility(View.GONE);
        new Handler().postDelayed(() -> {
            mapDiceToUi(n);
            highlightTokens();
        }, mapDiceUi);
        pt = playerNo;
        setTokensClickableFalse();
        rollDice.setClickable(false);
        if (playerNo % 4 == 1) {
            playerOne();
        } else if (playerNo % 4 == 2) {
            playerTwo();
        } else if (playerNo % 4 == 3) {
            playerThree();
        } else if (playerNo % 4 == 0) {
            playerFour();
        }
    }

    private void playerOne() {
        if (p11 == 1) {
            iv1.setOnClickListener(this);
        }
        if (p12 == 1) {
            iv2.setOnClickListener(this);
        }
        if (p13 == 1) {
            iv3.setOnClickListener(this);
        }
        if (p14 == 1) {
            iv4.setOnClickListener(this);
        }

        if (n == 6) {
            r = 1;
            iv1.setOnClickListener(this);
            iv2.setOnClickListener(this);
            iv3.setOnClickListener(this);
            iv4.setOnClickListener(this);
        } else if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
            new Handler().postDelayed(() -> {
                playerNo++;

            }, updatePlayerNo);
        }
        auto1();
        n1 = x1 - 51 + n;
        n2 = x2 - 51 + n;
        n3 = x3 - 51 + n;
        n4 = x4 - 51 + n;
        if (n1 == 6) {
            iv1.setClickable(true);
        }
        if (n2 == 6) {
            iv2.setOnClickListener(this);
        }
        if (n3 == 6) {
            iv3.setOnClickListener(this);
        }
        if (n4 == 6) {
            iv4.setOnClickListener(this);
        }
        if (iv1.getVisibility() == View.INVISIBLE)
            iv1.setClickable(false);
        if (iv2.getVisibility() == View.INVISIBLE)
            iv2.setClickable(false);
        if (iv3.getVisibility() == View.INVISIBLE)
            iv3.setClickable(false);
        if (iv4.getVisibility() == View.INVISIBLE)
            iv4.setClickable(false);
        if ((!iv1.isClickable() || !home1hig(iv1)) && (!iv2.isClickable() || !home1hig(iv2)) && (!iv3.isClickable() || !home1hig(iv3)) && (!iv4.isClickable() || !home1hig(iv4)) || r == 0) {
            setDiceClickable();
        }
    }

    private void playerTwo() {
        if (p31 == 1) {
            iv5.setOnClickListener(this);
        }
        if (p32 == 1) {
            iv6.setOnClickListener(this);
        }
        if (p33 == 1) {
            iv7.setOnClickListener(this);
        }
        if (p34 == 1) {
            iv8.setOnClickListener(this);
        }
        if (n == 6) {
            g = 1;
            iv5.setOnClickListener(this);
            iv6.setOnClickListener(this);
            iv7.setOnClickListener(this);
            iv8.setOnClickListener(this);
        } else if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
            new Handler().postDelayed(() -> {
                playerNo++;
            }, updatePlayerNo);
        }
        auto2();
        n1 = n + x5 - 12;
        n2 = n + x6 - 12;
        n3 = n + x7 - 12;
        n4 = n + x8 - 12;

        if (n1 == 6 && iv5.getTop() < top + 7 * d && iv5.getLeft() == 7 * d) {
            iv5.setOnClickListener(this);
        }
        if (n2 == 6 && iv6.getTop() < top + 7 * d && iv6.getLeft() == 7 * d) {
            iv6.setOnClickListener(this);
        }
        if (n3 == 6 && iv7.getTop() < top + 7 * d && iv7.getLeft() == 7 * d) {
            iv7.setOnClickListener(this);
        }
        if (n4 == 6 && iv8.getTop() < top + 7 * d && iv8.getLeft() == 7 * d) {
            iv8.setOnClickListener(this);
        }

        if (iv5.getVisibility() == View.INVISIBLE)
            iv5.setClickable(false);
        if (iv6.getVisibility() == View.INVISIBLE)
            iv6.setClickable(false);
        if (iv7.getVisibility() == View.INVISIBLE)
            iv7.setClickable(false);
        if (
                iv8.getVisibility() == View.INVISIBLE)
            iv8.setClickable(false);
        if ((!iv5.isClickable() || !home2hig(iv5)) && (!iv6.isClickable() || !home2hig(iv6)) && (!iv7.isClickable() || !home2hig(iv7)) && (!iv8.isClickable() || !home2hig(iv8)) || g == 0) {
            setDiceClickable();
        }
    }

    private void playerThree() {
        if (p21 == 1) {
            iv9.setOnClickListener(this);
        }
        if (p22 == 1) {
            iv10.setOnClickListener(this);
        }
        if (p23 == 1) {
            iv11.setOnClickListener(this);
        }
        if (p24 == 1) {
            iv12.setOnClickListener(this);
        }
        if (n == 6) {
            b = 1;
            iv9.setOnClickListener(this);
            iv10.setOnClickListener(this);
            iv11.setOnClickListener(this);
            iv12.setOnClickListener(this);
        } else if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
            new Handler().postDelayed(() -> {
                playerNo++;
            }, updatePlayerNo);
        }
        auto3();
        n1 = n + x9 - 25;
        n2 = n + x10 - 25;
        n3 = n + x11 - 25;
        n4 = n + x12 - 25;
        if (n1 == 6 && iv9.getLeft() > 7 * d && iv9.getTop() == top + 7 * d) {
            iv9.setOnClickListener(this);
        }
        if (n2 == 6 && iv10.getLeft() > 7 * d && iv10.getTop() == top + 7 * d) {
            iv10.setOnClickListener(this);
        }
        if (n3 == 6 && iv11.getLeft() > 7 * d && iv11.getTop() == top + 7 * d) {
            iv11.setOnClickListener(this);
        }
        if (n4 == 6 && iv12.getLeft() > 7 * d && iv12.getTop() == top + 7 * d) {
            iv12.setOnClickListener(this);
        }

        if (iv9.getVisibility() == View.INVISIBLE)
            iv9.setClickable(false);
        if (iv10.getVisibility() == View.INVISIBLE)
            iv10.setClickable(false);
        if (iv11.getVisibility() == View.INVISIBLE)
            iv11.setClickable(false);
        if (iv12.getVisibility() == View.INVISIBLE)
            iv12.setClickable(false);
        if ((!iv9.isClickable() || !home3hig(iv9)) && (!iv10.isClickable() || !home3hig(iv10)) && (!iv11.isClickable() || !home3hig(iv11)) && (!iv12.isClickable() || !home3hig(iv12)) || b == 0) {
            setDiceClickable();
        }
    }

    private void playerFour() {
        if (p41 == 1) {
            iv13.setOnClickListener(this);
        }
        if (p42 == 1) {
            iv14.setOnClickListener(this);
        }
        if (p43 == 1) {
            iv15.setOnClickListener(this);
        }
        if (p44 == 1) {
            iv16.setOnClickListener(this);
        }
        if (n == 6) {
            y = 1;
            iv13.setOnClickListener(this);
            iv14.setOnClickListener(this);
            iv15.setOnClickListener(this);
            iv16.setOnClickListener(this);
        } else if (n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
            new Handler().postDelayed(() -> {
                playerNo++;
            }, updatePlayerNo);
        }
        auto4();
        n1 = n + x13 - 38;
        n2 = n + x14 - 38;
        n3 = n + x15 - 38;
        n4 = n + x16 - 38;
        if (n1 == 6 && iv13.getTop() > top + 6 * d && iv13.getLeft() == 7 * d) {
            iv13.setOnClickListener(this);
        }
        if (n2 == 6 && iv14.getTop() > top + 6 * d && iv14.getLeft() == 7 * d) {
            iv14.setOnClickListener(this);
        }
        if (n3 == 6 && iv15.getTop() > top + 6 * d && iv15.getLeft() == 7 * d) {
            iv15.setOnClickListener(this);
        }
        if (n4 == 6 && iv16.getTop() > top + 6 * d && iv16.getLeft() == 7 * d) {
            iv16.setOnClickListener(this);
        }

        if (iv13.getVisibility() == View.INVISIBLE)
            iv13.setClickable(false);
        if (iv14.getVisibility() == View.INVISIBLE)
            iv14.setClickable(false);
        if (iv15.getVisibility() == View.INVISIBLE)
            iv15.setClickable(false);
        if (iv16.getVisibility() == View.INVISIBLE)
            iv16.setClickable(false);
        if ((!iv13.isClickable() || !home4high(iv14)) && (!iv14.isClickable() || !home4high(iv14)) && (!iv15.isClickable() || !home4high(iv15)) && (!iv16.isClickable() || !home4high(iv16)) || y == 0) {
            setDiceClickable();
        }
    }

    private void auto1() {
        //changeDicePosi = autoRunToken-changeDicePosi;
        if (iv1.isClickable() || iv2.isClickable() || iv3.isClickable() || iv4.isClickable())
            pbTimer.setVisibility(View.VISIBLE);
        else return;
        t = 0;
        cdTimer1 = new CountDownTimer(autoRunToken, cd) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer.setProgress(t);
                t++;
            }

            @Override
            public void onFinish() {
                t++;
                if (iv1.isClickable() && !iv1h && iv1.getVisibility() == View.VISIBLE) token1(iv1);
                else if (iv2.isClickable() && !iv2h && iv2.getVisibility() == View.VISIBLE)
                    token2(iv2);
                else if (iv3.isClickable() && !iv3h && iv3.getVisibility() == View.VISIBLE)
                    token3(iv3);
                else if (iv4.isClickable() && !iv4h && iv4.getVisibility() == View.VISIBLE)
                    token4(iv4);
                else return;
                pbTimer.setProgress(0);
            }
        };
        cdTimer1.start();
    }

    private void auto2() {
        //changeDicePosi = autoRunToken ;
        if (iv5.isClickable() || iv6.isClickable() || iv7.isClickable() || iv8.isClickable())
            pbTimer.setVisibility(View.VISIBLE);
        else return;
        t = 0;
        cdTimer2 = new CountDownTimer(autoRunToken, cd) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer.setProgress(t);
                t++;
            }

            @Override
            public void onFinish() {
                t++;
                if (iv5.isClickable() && !iv5h && iv5.getVisibility() == View.VISIBLE) token5(iv5);
                else if (iv6.isClickable() && !iv6h && iv6.getVisibility() == View.VISIBLE)
                    token6(iv6);
                else if (iv7.isClickable() && !iv7h && iv7.getVisibility() == View.VISIBLE)
                    token7(iv7);
                else if (iv8.isClickable() && !iv8h && iv8.getVisibility() == View.VISIBLE)
                    token8(iv8);
                else return;
                pbTimer.setProgress(0);
            }
        };
        cdTimer2.start();
    }

    private void auto3() {
        //changeDicePosi = autoRunToken-changeDicePosi ;
        if (iv9.isClickable() || iv10.isClickable() || iv11.isClickable() || iv12.isClickable())
            pbTimer.setVisibility(View.VISIBLE);
        else return;
        t = 0;
        cdTimer3 = new CountDownTimer(autoRunToken, cd) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer.setProgress(t);
                t++;
            }

            @Override
            public void onFinish() {
                t++;
                if (iv9.isClickable() && !iv9h && iv9.getVisibility() == View.VISIBLE) token9(iv9);
                else if (iv10.isClickable() && !iv10h && iv10.getVisibility() == View.VISIBLE)
                    token10(iv10);
                else if (iv11.isClickable() && !iv11h && iv11.getVisibility() == View.VISIBLE)
                    token11(iv11);
                else if (iv12.isClickable() && !iv12h && iv12.getVisibility() == View.VISIBLE)
                    token12(iv12);
                else return;
                pbTimer.setProgress(0);
            }
        };
        cdTimer3.start();
    }

    private void auto4() {
//        changeDicePosi = autoRunToken;
        if (iv13.isClickable() || iv14.isClickable() || iv15.isClickable() || iv16.isClickable())
            pbTimer.setVisibility(View.VISIBLE);
        else return;
        t = 0;
        cdTimer4 = new CountDownTimer(autoRunToken, cd) {
            @Override
            public void onTick(long millisUntilFinished) {
                pbTimer.setProgress(t);
                t++;
            }

            @Override
            public void onFinish() {
                t++;
                if (iv13.isClickable() && !iv13h && iv13.getVisibility() == View.VISIBLE)
                    token13(iv13);
                else if (iv14.isClickable() && !iv14h && iv14.getVisibility() == View.VISIBLE)
                    token14(iv14);
                else if (iv15.isClickable() && !iv15h && iv15.getVisibility() == View.VISIBLE)
                    token15(iv15);
                else if (iv16.isClickable() && !iv16h && iv16.getVisibility() == View.VISIBLE)
                    token16(iv16);
                pbTimer.setProgress(0);
            }
        };
        cdTimer4.start();
    }

    private void gameover() {
        if (iv1.getVisibility() == View.INVISIBLE && iv2.getVisibility() == View.INVISIBLE &&
                iv3.getVisibility() == View.INVISIBLE && iv4.getVisibility() == View.INVISIBLE) {
            openResultActivity(2);
        }
        if (iv5.getVisibility() == View.INVISIBLE && iv6.getVisibility() == View.INVISIBLE &&
                iv7.getVisibility() == View.INVISIBLE && iv8.getVisibility() == View.INVISIBLE) {
            openResultActivity(3);
        }
        if (iv9.getVisibility() == View.INVISIBLE && iv10.getVisibility() == View.INVISIBLE &&
                iv11.getVisibility() == View.INVISIBLE && iv12.getVisibility() == View.INVISIBLE) {
            openResultActivity(4);
        }
        if (iv13.getVisibility() == View.INVISIBLE && iv14.getVisibility() == View.INVISIBLE &&
                iv15.getVisibility() == View.INVISIBLE && iv16.getVisibility() == View.INVISIBLE) {
            openResultActivity(1);
        }
    }

    private void openResultActivity(int pNo) {
        Intent intent = new Intent(this, GameResultActivity.class);
        intent.putExtra(WINNER, pNo);
        startActivity(intent);
    }

    private void hideHighlighters() {
        in1.clearAnimation();
        in2.clearAnimation();
        in3.clearAnimation();
        in4.clearAnimation();
        in1.setVisibility(View.GONE);
        in2.setVisibility(View.GONE);
        in3.setVisibility(View.GONE);
        in4.setVisibility(View.GONE);
        setTokensClickableFalse();
    }

    private boolean home3hig(ImageView iv) {

        if (iv.getTop() == top + 7 * d && iv.getLeft() > 6 * d) {
            if ((n == 6 && iv.getLeft() < 14 * d) ||
                    (n == 5 && iv.getLeft() < 13 * d) ||
                    (n == 4 && iv.getLeft() < 12 * d) ||
                    (n == 3 && iv.getLeft() < 11 * d) ||
                    (n == 2 && iv.getLeft() < 10 * d) ||
                    (n == 1 && iv.getLeft() < 9 * d)) {
                iv.setClickable(false);
                return false;
            }
        }
        return true;
    }

    private boolean home2hig(ImageView iv) {

        if (iv.getLeft() == 7 * d && iv.getTop() < top + 7 * d) {
            if ((n == 6 && iv.getTop() > top + d) ||
                    (n == 5 && iv.getTop() > top + 2 * d) ||
                    (n == 4 && iv.getTop() > top + 3 * d) ||
                    (n == 3 && iv.getTop() > top + 4 * d) ||
                    (n == 2 && iv.getTop() > top + 5 * d) ||
                    (n == 1 && iv.getTop() > top + 6 * d)) {
                iv.setClickable(false);
                return false;
            }
        }
        return true;
    }

    private boolean home1hig(ImageView iv) {

        if (iv.getTop() == top + 7 * d && iv.getLeft() < 7 * d) {
            if ((n == 6 && iv.getLeft() > 0) ||
                    (n == 5 && iv.getLeft() > d) ||
                    (n == 4 && iv.getLeft() > 2 * d) ||
                    (n == 3 && iv.getLeft() > 3 * d) ||
                    (n == 2 && iv.getLeft() > 4 * d) ||
                    (n == 1 && iv.getLeft() > 5 * d)) {
                iv.setClickable(false);
                return false;
            }
        }
        return true;
    }

    private boolean home4high(ImageView iv) {

        if (iv.getLeft() == 7 * d && iv.getTop() > top + 6 * d) {
            if ((n == 6 && iv.getTop() < top + 14 * d) ||
                    (n == 5 && iv.getTop() < top + 13 * d) ||
                    (n == 4 && iv.getTop() < top + 12 * d) ||
                    (n == 3 && iv.getTop() < top + 11 * d) ||
                    (n == 2 && iv.getTop() < top + 10 * d) ||
                    (n == 1 && iv.getTop() < top + 9 * d)) {
                iv.setClickable(false);
                return false;
            }
        }
        return true;
    }

    private void highlightToken4() {
        int l = 5 * d;
        int t = top + 10 * d;

        if (!(iv13.getLeft() < l && iv13.getTop() > t) && !iv13h && home4high(iv13)) {
            highlightToken(in1, iv13.getLeft(), iv13.getTop());
        }
        if (!(iv14.getLeft() < l && iv14.getTop() > t) && !iv14h && home4high(iv14)) {
            highlightToken(in2, iv14.getLeft(), iv14.getTop());
        }
        if (!(iv15.getLeft() < l && iv15.getTop() > t) && !iv15h && home4high(iv15)) {
            highlightToken(in3, iv15.getLeft(), iv15.getTop());
        }
        if (!(iv16.getLeft() < l && iv16.getTop() > t) && !iv16h && home4high(iv16)) {
            highlightToken(in4, iv16.getLeft(), iv16.getTop());
        }
    }

    private void highlightToken3() {
        int l = 10 * d;
        int t = top + 10 * d;

        if (!(iv9.getLeft() > l && iv9.getTop() > t) && !iv9h && home3hig(iv9)) {
            highlightToken(in1, iv9.getLeft(), iv9.getTop());
        }
        if (!(iv10.getLeft() > l && iv10.getTop() > t) && !iv10h && home3hig(iv10)) {
            highlightToken(in2, iv10.getLeft(), iv10.getTop());
        }
        if (!(iv11.getLeft() > l && iv11.getTop() > t) && !iv11h && home3hig(iv11)) {
            highlightToken(in3, iv11.getLeft(), iv11.getTop());
        }
        if (!(iv12.getLeft() > l && iv12.getTop() > t) && !iv12h && home3hig(iv12)) {
            highlightToken(in4, iv12.getLeft(), iv12.getTop());
        }
    }

    private void highlightToken2() {
        int l = 10 * d;
        int t = top + 5 * d;

        if (!(iv5.getLeft() > l && iv5.getTop() < t) && !iv5h && home2hig(iv5)) {
            highlightToken(in1, iv5.getLeft(), iv5.getTop());
        }
        if (!(iv6.getLeft() > l && iv6.getTop() < t) && !iv6h && home2hig(iv6)) {
            highlightToken(in2, iv6.getLeft(), iv6.getTop());
        }
        if (!(iv7.getLeft() > l && iv7.getTop() < t) && !iv7h && home2hig(iv7)) {
            highlightToken(in3, iv7.getLeft(), iv7.getTop());
        }
        if (!(iv8.getLeft() > l && iv8.getTop() < t) && !iv8h && home2hig(iv8)) {
            highlightToken(in4, iv8.getLeft(), iv8.getTop());
        }
    }

    private void highlightToken1() {
        int l = 5 * d;
        int t = top + 5 * d;

        if (!(iv1.getLeft() < l && iv1.getTop() < t) && !iv1h && home1hig(iv1)) {
            highlightToken(in1, iv1.getLeft(), iv1.getTop());
        }
        if (!(iv2.getLeft() < l && iv2.getTop() < t) && !iv2h && home1hig(iv2)) {
            highlightToken(in2, iv2.getLeft(), iv2.getTop());
        }
        if (!(iv3.getLeft() < l && iv3.getTop() < t) && !iv3h && home1hig(iv3)) {
            highlightToken(in3, iv3.getLeft(), iv3.getTop());
        }
        if (!(iv4.getLeft() < l && iv4.getTop() < t) && !iv4h && home1hig(iv4)) {
            highlightToken(in4, iv4.getLeft(), iv4.getTop());
        }
    }

    private void highlightTokens() {
        if (playerNo % 4 == 1) {
            if (n == 6) {
                if (!iv1h && home1hig(iv1)) highlightToken(in1, iv1.getLeft(), iv1.getTop());
                if (!iv2h && home1hig(iv2)) highlightToken(in2, iv2.getLeft(), iv2.getTop());
                if (!iv3h && home1hig(iv3)) highlightToken(in3, iv3.getLeft(), iv3.getTop());
                if (!iv4h && home1hig(iv4)) highlightToken(in4, iv4.getLeft(), iv4.getTop());
            } else {
                highlightToken1();
            }
        } else if (playerNo % 4 == 2) {
            if (n == 6) {
                if (!iv5h && home2hig(iv5)) highlightToken(in1, iv5.getLeft(), iv5.getTop());
                if (!iv6h && home2hig(iv6)) highlightToken(in2, iv6.getLeft(), iv6.getTop());
                if (!iv7h && home2hig(iv7)) highlightToken(in3, iv7.getLeft(), iv7.getTop());
                if (!iv8h && home2hig(iv8)) highlightToken(in4, iv8.getLeft(), iv8.getTop());
            } else {
                highlightToken2();
            }
        } else if (playerNo % 4 == 3) {
            if (n == 6) {
                if (!iv9h && home3hig(iv9)) highlightToken(in1, iv9.getLeft(), iv9.getTop());
                if (!iv10h && home3hig(iv10)) highlightToken(in2, iv10.getLeft(), iv10.getTop());
                if (!iv11h && home3hig(iv11)) highlightToken(in3, iv11.getLeft(), iv11.getTop());
                if (!iv12h && home3hig(iv12)) highlightToken(in4, iv12.getLeft(), iv12.getTop());
            } else {
                highlightToken3();
            }
        } else if (playerNo % 4 == 0) {
            if (n == 6) {
                if (!iv13h && home4high(iv13)) highlightToken(in1, iv13.getLeft(), iv13.getTop());
                if (!iv14h && home4high(iv14)) highlightToken(in2, iv14.getLeft(), iv14.getTop());
                if (!iv15h && home4high(iv15)) highlightToken(in3, iv15.getLeft(), iv15.getTop());
                if (!iv16h && home4high(iv16)) highlightToken(in4, iv16.getLeft(), iv16.getTop());
            } else {
                highlightToken4();
            }
        }
    }

    private void highlightToken(ImageView iv, int ml, int mt) {
        iv.setVisibility(View.VISIBLE);
        iv.bringToFront();
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        mP.leftMargin = ml;
        mP.topMargin = mt;
        iv.setLayoutParams(mP);
        Animation highlightToken = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_token);
        highlightToken.setFillAfter(true);
        iv.startAnimation(highlightToken);
    }

    private int positionOf(int x, ImageView ivx) {

        setDiceClickable();
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) ivx.getLayoutParams();
        x = x + n;
        int a = ivx.getId();
        if (a == R.id.t5 || a == R.id.t6 || a == R.id.t7 || a == R.id.t8
                || a == R.id.t9 || a == R.id.t10 || a == R.id.t11 || a == R.id.t12
                || a == R.id.t13 || a == R.id.t14 || a == R.id.t15 || a == R.id.t16) {
            if (x > 52)
                x = x - 52;
        }
        if (a == R.id.t1 || a == R.id.t2 || a == R.id.t3 || a == R.id.t4) {
            extraN = x - 51;
            if (x > 51) {
                homePath1(extraN, ivx);
            } else
                theCase(x, ivx);
        } else if (a == R.id.t5 || a == R.id.t6 || a == R.id.t7 || a == R.id.t8) {
            if (mParams.leftMargin <= 7 * d && x > 12 && x < 24) {
                extraN = x - 12;
                homePath2(extraN, ivx);
            } else
                theCase(x, ivx);
        } else if (a == R.id.t9 || a == R.id.t10 || a == R.id.t11 || a == R.id.t12) {
            extraN = x - 25;
            if (mParams.topMargin <= top + 7 * d && x > 25) {
                homePath3(extraN, ivx);
            } else
                theCase(x, ivx);
        } else if (a == R.id.t13 || a == R.id.t14 || a == R.id.t15 || a == R.id.t16) {
            extraN = x - 38;
            if (mParams.leftMargin >= 7 * d && x > 38) {
                homePath4(extraN, ivx);
            } else
                theCase(x, ivx);
        }
        return x;
    }

    private void homePath4(int extra, ImageView ivx) {
        if (ivx == iv13) updateTokenFb(TOKEN13, "hp13" + extra);
        else if (ivx == iv14) updateTokenFb(TOKEN14, "hp14" + extra);
        else if (ivx == iv15) updateTokenFb(TOKEN15, "hp15" + extra);
        else if (ivx == iv16) updateTokenFb(TOKEN16, "hp16" + extra);

        Log.d(TAG, "homePath4: " + extra);
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) ivx.getLayoutParams();
        Log.d(TAG, "homePath4: " + ludo.getExtra());
        switch (extra) {
            case 1:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 13 * d;
                break;
            case 2:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 12 * d;
                break;
            case 3:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 11 * d;
                break;
            case 4:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 10 * d;
                break;
            case 5:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 9 * d;
                break;
        }
        ivx.setLayoutParams(mParams);
        updateXFb(EXTRA, extra);
    }

    private void homePath3(int extra, ImageView ivx) {
        if (ivx == iv9) updateTokenFb(TOKEN9, "hp9" + extra);
        else if (ivx == iv10) updateTokenFb(TOKEN10, "hp10" + extra);
        else if (ivx == iv11) updateTokenFb(TOKEN11, "hp11" + extra);
        else if (ivx == iv12) updateTokenFb(TOKEN12, "hp12" + extra);

        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) ivx.getLayoutParams();
        switch (extra) {
            case 1:
                mParams.leftMargin = 13 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 2:
                mParams.leftMargin = 12 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 3:
                mParams.leftMargin = 11 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 4:
                mParams.leftMargin = 10 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 5:
                mParams.leftMargin = 9 * d;
                mParams.topMargin = top + 7 * d;
                break;
        }
        ivx.setLayoutParams(mParams);
    }

    private void homePath2(int extra, ImageView ivx) {
        if (ivx == iv5) updateTokenFb(TOKEN5, "hp5" + extra);
        else if (ivx == iv6) updateTokenFb(TOKEN6, "hp6" + extra);
        else if (ivx == iv7) updateTokenFb(TOKEN7, "hp7" + extra);
        else if (ivx == iv8) updateTokenFb(TOKEN8, "hp8" + extra);

        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) ivx.getLayoutParams();
        switch (extra) {
            case 1:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + d;
                break;
            case 2:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 2 * d;
                break;
            case 3:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 3 * d;
                break;
            case 4:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 4 * d;
                break;
            case 5:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 5 * d;
                break;
        }
        ivx.setLayoutParams(mParams);
    }

    private void homePath1(int extra, ImageView ivx) {
        if (ivx == iv1) updateTokenFb(TOKEN1, "hp1" + extra);
        else if (ivx == iv2) updateTokenFb(TOKEN2, "hp2" + extra);
        else if (ivx == iv3) updateTokenFb(TOKEN3, "hp3" + extra);
        else if (ivx == iv4) updateTokenFb(TOKEN4, "hp4" + extra);
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) ivx.getLayoutParams();
        switch (extra) {
            case 1:
                mParams.leftMargin = d;
                mParams.topMargin = top + 7 * d;
                break;
            case 2:
                mParams.leftMargin = 2 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 3:
                mParams.leftMargin = 3 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 4:
                mParams.leftMargin = 4 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 5:
                mParams.leftMargin = 5 * d;
                mParams.topMargin = top + 7 * d;
                break;
        }
        ivx.setLayoutParams(mParams);
    }

    public void theCase(int p, ImageView pp) {
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) pp.getLayoutParams();
        if (pp == iv1) updateTokenFb(TOKEN1, String.valueOf(p));
        else if (pp == iv2) updateTokenFb(TOKEN2, String.valueOf(p));
        else if (pp == iv3) updateTokenFb(TOKEN3, String.valueOf(p));
        else if (pp == iv4) updateTokenFb(TOKEN4, String.valueOf(p));
        else if (pp == iv5) updateTokenFb(TOKEN5, String.valueOf(p));
        else if (pp == iv6) updateTokenFb(TOKEN6, String.valueOf(p));
        else if (pp == iv7) updateTokenFb(TOKEN7, String.valueOf(p));
        else if (pp == iv8) updateTokenFb(TOKEN8, String.valueOf(p));
        else if (pp == iv9) updateTokenFb(TOKEN9, String.valueOf(p));
        else if (pp == iv10) updateTokenFb(TOKEN10, String.valueOf(p));
        else if (pp == iv11) updateTokenFb(TOKEN11, String.valueOf(p));
        else if (pp == iv12) updateTokenFb(TOKEN12, String.valueOf(p));
        else if (pp == iv13) updateTokenFb(TOKEN13, String.valueOf(p));
        else if (pp == iv14) updateTokenFb(TOKEN14, String.valueOf(p));
        else if (pp == iv15) updateTokenFb(TOKEN15, String.valueOf(p));
        else if (pp == iv16) updateTokenFb(TOKEN16, String.valueOf(p));
        switch (p) {
            case 1:
                mParams.leftMargin = d;
                mParams.topMargin = top + 6 * d;
                break;
            case 2:
                mParams.leftMargin = 2 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 3:
                mParams.leftMargin = 3 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 4:
                mParams.leftMargin = 4 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 5:
                mParams.leftMargin = 5 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 6:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 5 * d;
                break;
            case 7:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 4 * d;
                break;
            case 8:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 3 * d;
                break;
            case 9:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 2 * d;
                break;
            case 10:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + d;
                break;
            case 11:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top;
                break;
            case 12:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top;
                break;
            case 13:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top;
                break;
            case 14:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + d;
                break;
            case 15:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 2 * d;
                break;
            case 16:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 3 * d;
                break;
            case 17:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 4 * d;
                break;
            case 18:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 5 * d;
                break;
            case 19:
                mParams.leftMargin = 9 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 20:
                mParams.leftMargin = 10 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 21:
                mParams.leftMargin = 11 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 22:
                mParams.leftMargin = 12 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 23:
                mParams.leftMargin = 13 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 24:
                mParams.leftMargin = 14 * d;
                mParams.topMargin = top + 6 * d;
                break;
            case 25:
                mParams.leftMargin = 14 * d;
                mParams.topMargin = top + 7 * d;
                break;
            case 26:
                mParams.leftMargin = 14 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 27:
                mParams.leftMargin = 13 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 28:
                mParams.leftMargin = 12 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 29:
                mParams.leftMargin = 11 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 30:
                mParams.leftMargin = 10 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 31:
                mParams.leftMargin = 9 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 32:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 9 * d;
                break;
            case 33:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 10 * d;
                break;
            case 34:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 11 * d;
                break;
            case 35:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 12 * d;
                break;
            case 36:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 13 * d;
                break;
            case 37:
                mParams.leftMargin = 8 * d;
                mParams.topMargin = top + 14 * d;
                break;
            case 38:
                mParams.leftMargin = 7 * d;
                mParams.topMargin = top + 14 * d;
                break;
            case 39:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 14 * d;
                break;
            case 40:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 13 * d;
                break;
            case 41:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 12 * d;
                break;
            case 42:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 11 * d;
                break;
            case 43:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 10 * d;
                break;
            case 44:
                mParams.leftMargin = 6 * d;
                mParams.topMargin = top + 9 * d;
                break;
            case 45:
                mParams.leftMargin = 5 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 46:
                mParams.leftMargin = 4 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 47:
                mParams.leftMargin = 3 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 48:
                mParams.leftMargin = 2 * d;
                mParams.topMargin = top + 8 * d;
                break;
            case 49:
                mParams.leftMargin = d;
                mParams.topMargin = top + 8 * d;
                break;
            case 50:
                mParams.leftMargin = 0;
                mParams.topMargin = top + 8 * d;
                break;
            case 51:
                mParams.leftMargin = 0;
                mParams.topMargin = top + 7 * d;
                break;
            case 52:
                mParams.leftMargin = 0;
                mParams.topMargin = top + 6 * d;
                break;
        }
        pp.setLayoutParams(mParams);
    }

    private boolean isSafe(int lm, int tm) {
        //to check if token lies in safe checkpoints
        if (lm == c1.getLeft() && tm == c1.getTop()) return true;
        if (lm == c2.getLeft() && tm == c2.getTop()) return true;
        if (lm == c3.getLeft() && tm == c3.getTop()) return true;
        if (lm == c4.getLeft() && tm == c4.getTop()) return true;
        if (lm == c5.getLeft() && tm == c5.getTop()) return true;
        if (lm == c6.getLeft() && tm == c6.getTop()) return true;
        if (lm == c7.getLeft() && tm == c7.getTop()) return true;
        return lm == c8.getLeft() && tm == c8.getTop();
    }

    public void checkPosition(ImageView v) {
        //to check if one token overlaps over another then to move previous token in initial state if it is not checkpoint
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) v.getLayoutParams();
        int b = v.getId();

        if (isSafe(mP.leftMargin, mP.topMargin)) return;

        if (b == R.id.t1 || b == R.id.t2 || b == R.id.t3 || b == R.id.t4) {
            int lm1 = iv5.getLeft();
            int tm1 = iv5.getTop();
            if (lm1 == mP.leftMargin && tm1 == mP.topMargin) {
                fill2(iv5);
            }

            int lm2 = iv6.getLeft();
            int tm2 = iv6.getTop();
            if (lm2 == mP.leftMargin && tm2 == mP.topMargin) {
                fill2(iv6);
            }

            int lm3 = iv7.getLeft();
            int tm3 = iv7.getTop();
            if (lm3 == mP.leftMargin && tm3 == mP.topMargin) {
                fill2(iv7);
            }

            int lm4 = iv8.getLeft();
            int tm4 = iv8.getTop();
            if (lm4 == mP.leftMargin && tm4 == mP.topMargin) {
                fill2(iv8);
            }

            int lm5 = iv9.getLeft();
            int tm5 = iv9.getTop();
            if (lm5 == mP.leftMargin && tm5 == mP.topMargin) {
                fill3(iv9);
            }

            int lm6 = iv10.getLeft();
            int tm6 = iv10.getTop();
            if (lm6 == mP.leftMargin && tm6 == mP.topMargin) {
                fill3(iv10);
            }

            int lm7 = iv11.getLeft();
            int tm7 = iv11.getTop();
            if (lm7 == mP.leftMargin && tm7 == mP.topMargin) {
                fill3(iv11);
            }

            int lm8 = iv12.getLeft();
            int tm8 = iv12.getTop();
            if (lm8 == mP.leftMargin && tm8 == mP.topMargin) {
                fill3(iv12);
            }

            int lm9 = iv13.getLeft();
            int tm9 = iv13.getTop();
            if (lm9 == mP.leftMargin && tm9 == mP.topMargin) {
                fill4(iv13);
            }

            int lm10 = iv14.getLeft();
            int tm10 = iv14.getTop();
            if (lm10 == mP.leftMargin && tm10 == mP.topMargin) {
                fill4(iv14);
            }

            int lm11 = iv15.getLeft();
            int tm11 = iv15.getTop();
            if (lm11 == mP.leftMargin && tm11 == mP.topMargin) {
                fill4(iv15);
            }

            int lm12 = iv16.getLeft();
            int tm12 = iv16.getTop();
            if (lm12 == mP.leftMargin && tm12 == mP.topMargin) {
                fill4(iv16);
            }

        }
        if (b == R.id.t5 || b == R.id.t6 || b == R.id.t7 || b == R.id.t8) {
            int lm1 = iv1.getLeft();
            int tm1 = iv1.getTop();
            if (lm1 == mP.leftMargin && tm1 == mP.topMargin) {
                fill1(iv1);
            }

            int lm2 = iv2.getLeft();
            int tm2 = iv2.getTop();
            if (lm2 == mP.leftMargin && tm2 == mP.topMargin) {
                fill1(iv2);
            }

            int lm3 = iv3.getLeft();
            int tm3 = iv3.getTop();
            if (lm3 == mP.leftMargin && tm3 == mP.topMargin) {
                fill1(iv3);
            }

            int lm4 = iv4.getLeft();
            int tm4 = iv4.getTop();
            if (lm4 == mP.leftMargin && tm4 == mP.topMargin) {
                fill1(iv4);
            }

            int lm5 = iv9.getLeft();
            int tm5 = iv9.getTop();
            if (lm5 == mP.leftMargin && tm5 == mP.topMargin) {
                fill3(iv9);
            }

            int lm6 = iv10.getLeft();
            int tm6 = iv10.getTop();
            if (lm6 == mP.leftMargin && tm6 == mP.topMargin) {
                fill3(iv10);
            }

            int lm7 = iv11.getLeft();
            int tm7 = iv11.getTop();
            if (lm7 == mP.leftMargin && tm7 == mP.topMargin) {
                fill3(iv11);
            }

            int lm8 = iv12.getLeft();
            int tm8 = iv12.getTop();
            if (lm8 == mP.leftMargin && tm8 == mP.topMargin) {
                fill3(iv12);
            }

            int lm9 = iv13.getLeft();
            int tm9 = iv13.getTop();
            if (lm9 == mP.leftMargin && tm9 == mP.topMargin) {
                fill4(iv13);
            }

            int lm10 = iv14.getLeft();
            int tm10 = iv14.getTop();
            if (lm10 == mP.leftMargin && tm10 == mP.topMargin) {
                fill4(iv14);
            }

            int lm11 = iv15.getLeft();
            int tm11 = iv15.getTop();
            if (lm11 == mP.leftMargin && tm11 == mP.topMargin) {
                fill4(iv15);
            }

            int lm12 = iv16.getLeft();
            int tm12 = iv16.getTop();
            if (lm12 == mP.leftMargin && tm12 == mP.topMargin) {
                fill4(iv16);
            }

        }
        if (b == R.id.t9 || b == R.id.t10 || b == R.id.t11 || b == R.id.t12) {
            int lm1 = iv1.getLeft();
            int tm1 = iv1.getTop();
            if (lm1 == mP.leftMargin && tm1 == mP.topMargin) {
                fill1(iv1);
            }

            int lm2 = iv2.getLeft();
            int tm2 = iv2.getTop();
            if (lm2 == mP.leftMargin && tm2 == mP.topMargin) {
                fill1(iv2);
            }

            int lm3 = iv3.getLeft();
            int tm3 = iv3.getTop();
            if (lm3 == mP.leftMargin && tm3 == mP.topMargin) {
                fill1(iv3);
            }

            int lm4 = iv4.getLeft();
            int tm4 = iv4.getTop();
            if (lm4 == mP.leftMargin && tm4 == mP.topMargin) {
                fill1(iv4);
            }

            int lm5 = iv5.getLeft();
            int tm5 = iv5.getTop();
            if (lm5 == mP.leftMargin && tm5 == mP.topMargin) {
                fill2(iv5);
            }

            int lm6 = iv6.getLeft();
            int tm6 = iv6.getTop();
            if (lm6 == mP.leftMargin && tm6 == mP.topMargin) {
                fill2(iv6);
            }

            int lm7 = iv7.getLeft();
            int tm7 = iv7.getTop();
            if (lm7 == mP.leftMargin && tm7 == mP.topMargin) {
                fill2(iv7);
            }

            int lm8 = iv8.getLeft();
            int tm8 = iv8.getTop();
            if (lm8 == mP.leftMargin && tm8 == mP.topMargin) {
                fill2(iv8);
            }

            int lm9 = iv13.getLeft();
            int tm9 = iv13.getTop();
            if (lm9 == mP.leftMargin && tm9 == mP.topMargin) {
                fill4(iv13);
            }

            int lm10 = iv14.getLeft();
            int tm10 = iv14.getTop();
            if (lm10 == mP.leftMargin && tm10 == mP.topMargin) {
                fill4(iv14);
            }

            int lm11 = iv15.getLeft();
            int tm11 = iv15.getTop();
            if (lm11 == mP.leftMargin && tm11 == mP.topMargin) {
                fill4(iv15);
            }

            int lm12 = iv16.getLeft();
            int tm12 = iv16.getTop();
            if (lm12 == mP.leftMargin && tm12 == mP.topMargin) {
                fill4(iv16);
            }


        }
        if (b == R.id.t13 || b == R.id.t14 || b == R.id.t15 || b == R.id.t16) {
            int lm1 = iv1.getLeft();
            int tm1 = iv1.getTop();
            if (lm1 == mP.leftMargin && tm1 == mP.topMargin) {
                fill1(iv1);
            }

            int lm2 = iv2.getLeft();
            int tm2 = iv2.getTop();
            if (lm2 == mP.leftMargin && tm2 == mP.topMargin) {
                fill1(iv2);
            }

            int lm3 = iv3.getLeft();
            int tm3 = iv3.getTop();
            if (lm3 == mP.leftMargin && tm3 == mP.topMargin) {
                fill1(iv3);
            }

            int lm4 = iv4.getLeft();
            int tm4 = iv4.getTop();
            if (lm4 == mP.leftMargin && tm4 == mP.topMargin) {
                fill1(iv4);
            }

            int lm5 = iv5.getLeft();
            int tm5 = iv5.getTop();
            if (lm5 == mP.leftMargin && tm5 == mP.topMargin) {
                fill2(iv5);
            }

            int lm6 = iv6.getLeft();
            int tm6 = iv6.getTop();
            if (lm6 == mP.leftMargin && tm6 == mP.topMargin) {
                fill2(iv6);
            }

            int lm7 = iv7.getLeft();
            int tm7 = iv7.getTop();
            if (lm7 == mP.leftMargin && tm7 == mP.topMargin) {
                fill2(iv7);
            }

            int lm8 = iv8.getLeft();
            int tm8 = iv8.getTop();
            if (lm8 == mP.leftMargin && tm8 == mP.topMargin) {
                fill2(iv8);
            }
            int lm9 = iv9.getLeft();
            int tm9 = iv9.getTop();
            if (lm9 == mP.leftMargin && tm9 == mP.topMargin) {
                fill3(iv9);
            }

            int lm10 = iv10.getLeft();
            int tm10 = iv10.getTop();
            if (lm10 == mP.leftMargin && tm10 == mP.topMargin) {
                fill3(iv10);
            }

            int lm11 = iv11.getLeft();
            int tm11 = iv11.getTop();
            if (lm11 == mP.leftMargin && tm11 == mP.topMargin) {
                fill3(iv11);
            }

            int lm12 = iv12.getLeft();
            int tm12 = iv12.getTop();
            if (lm12 == mP.leftMargin && tm12 == mP.topMargin) {
                fill3(iv12);
            }
        }
    }

    public void startGame() {
        playerNo = 4;
        updatePlayer();
        setDiceClickable();
    }

    private void setDiceClickable() {

        new Handler().postDelayed(() -> {
            setTokensClickableFalse();
            rollDice.setClickable(true);
            rollDice.setOnClickListener(LudoMultiPlayerActivity.this);
            updateDicePosition();
        }, changeDicePosi);
        hideHighlighters();
        changeDicePosi = 900;
    }

    private void updateDicePosition() {
        isDiceRolled = true;
        cdTimer.cancel();
        dice.setImageResource(R.drawable.dice_icon);
        if (playerNo % 4 == 1) {
            setDicePosi(5, top - diceSize - 25);
            bringTokensToFront(iv1, iv2, iv3, iv4);
        } else if (playerNo % 4 == 2) {
            setDicePosi(width - diceSize - 15, top - diceSize - 25);
            bringTokensToFront(iv5, iv6, iv7, iv8);
        } else if (playerNo % 4 == 3) {
            setDicePosi(width - diceSize - 25, top + width + 5);
            bringTokensToFront(iv9, iv10, iv11, iv12);
        } else if (playerNo % 4 == 0) {
            setDicePosi(5, top + width + 5);
            bringTokensToFront(iv13, iv14, iv15, iv16);
        }
        updatePlayerFb();
        updatePlayer();
        Log.d(TAG, "updateDicePosition: " + playerNo);
    }

    private void bringTokensToFront(ImageView i1, ImageView i2, ImageView i3, ImageView i4) {
        i1.bringToFront();
        i2.bringToFront();
        i3.bringToFront();
        i4.bringToFront();
        setSameParams(i1);
        setSameParams(i2);
        setSameParams(i3);
        setSameParams(i4);
    }

    private void setSameParams(ImageView iv) {
        RelativeLayout.LayoutParams mP = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        mP.leftMargin = iv.getLeft();
        mP.topMargin = iv.getTop();
        iv.setLayoutParams(mP);
    }

    public void setTokensClickableFalse() {
        iv1.setClickable(false);
        iv2.setClickable(false);
        iv3.setClickable(false);
        iv4.setClickable(false);
        iv5.setClickable(false);
        iv6.setClickable(false);
        iv7.setClickable(false);
        iv8.setClickable(false);
        iv9.setClickable(false);
        iv10.setClickable(false);
        iv11.setClickable(false);
        iv12.setClickable(false);
        iv13.setClickable(false);
        iv14.setClickable(false);
        iv15.setClickable(false);
        iv16.setClickable(false);
        pbTimer.setVisibility(View.GONE);
        pbTimer.setProgress(0);
    }

    public void assign() {

        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        top = (height - width) / 2;
        bottom = top + width / 2;
        d = width / 15;
        diceSize = 3 * d;

        pbTimer = findViewById(R.id.pb_timer);
        dice = (ImageView) findViewById(R.id.dice);
        rollDice = findViewById(R.id.roll);
        animDice = findViewById(R.id.animDice);
        iv1 = (ImageView) findViewById(R.id.t1);
        iv2 = (ImageView) findViewById(R.id.t2);
        iv3 = (ImageView) findViewById(R.id.t3);
        iv4 = (ImageView) findViewById(R.id.t4);
        iv5 = (ImageView) findViewById(R.id.t5);
        iv6 = (ImageView) findViewById(R.id.t6);
        iv7 = (ImageView) findViewById(R.id.t7);
        iv8 = (ImageView) findViewById(R.id.t8);
        iv9 = (ImageView) findViewById(R.id.t9);
        iv10 = (ImageView) findViewById(R.id.t10);
        iv11 = (ImageView) findViewById(R.id.t11);
        iv12 = (ImageView) findViewById(R.id.t12);
        iv13 = (ImageView) findViewById(R.id.t13);
        iv14 = (ImageView) findViewById(R.id.t14);
        iv15 = (ImageView) findViewById(R.id.t15);
        iv16 = (ImageView) findViewById(R.id.t16);
        c1 = (ImageView) findViewById(R.id.c1);
        c2 = (ImageView) findViewById(R.id.c2);
        c3 = (ImageView) findViewById(R.id.c3);
        c4 = (ImageView) findViewById(R.id.c4);
        c5 = (ImageView) findViewById(R.id.c5);
        c6 = (ImageView) findViewById(R.id.c6);
        c7 = (ImageView) findViewById(R.id.c7);
        c8 = (ImageView) findViewById(R.id.c8);
        in1 = (ImageView) findViewById(R.id.in1);
        in2 = (ImageView) findViewById(R.id.in2);
        in3 = (ImageView) findViewById(R.id.in3);
        in4 = (ImageView) findViewById(R.id.in4);
        mcv = (MaterialCardView) findViewById(R.id.layout_dice);
    }

    public int generateRandom() {

        EditText et = findViewById(R.id.te);
        Random randy = new Random();
        n = randy.nextInt(6) + 1;
//        n = Integer.parseInt(et.getText().toString());

        HashMap<String, Object> map = new HashMap<>();
        map.put(DICE_NO, String.valueOf(n));
        dbRef.updateChildren(map);

        return n;
    }

    private void setcpparms(ImageView iv, int lm, int tm) {
        iv.getLayoutParams().height = d;
        iv.getLayoutParams().width = d;
        RelativeLayout.LayoutParams mParams1 = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        mParams1.leftMargin = lm;
        mParams1.topMargin = tm;
        iv.setLayoutParams(mParams1);
    }

    public void mapDiceToUi(int x) {

        animDice.setVisibility(View.GONE);
        dice.setVisibility(View.VISIBLE);
        if (x == 1) {
            dice.setImageResource(R.drawable.d_one);
        }
        if (x == 2) {
            dice.setImageResource(R.drawable.d_two);
        }
        if (x == 3) {
            dice.setImageResource(R.drawable.d_three);
        }
        if (x == 4) {
            dice.setImageResource(R.drawable.d_four);
        }
        if (x == 5) {
            dice.setImageResource(R.drawable.d_five);
        }
        if (x == 6) {
            dice.setImageResource(R.drawable.d_six);
        }
    }

    public void setDicePosi(int lm, int tm) {
        dice.getLayoutParams().height = diceSize;
        dice.getLayoutParams().width = diceSize;

        animDice.getLayoutParams().height = diceSize;
        animDice.getLayoutParams().width = diceSize;
        RelativeLayout.LayoutParams mParams = (RelativeLayout.LayoutParams) mcv.getLayoutParams();
        mParams.leftMargin = lm;
        mParams.topMargin = tm;
        mcv.setLayoutParams(mParams);
    }

    private void fillCheckPost(ImageView v) {
        switch (v.getId()) {

            case R.id.c1:
                setcpparms(c1, d, top + 6 * d);
                break;
            case R.id.c2:
                setcpparms(c2, 6 * d, top + 13 * d);
                break;
            case R.id.c3:
                setcpparms(c3, 8 * d, top + d);
                break;
            case R.id.c4:
                setcpparms(c4, 13 * d, top + 8 * d);
                break;
            case R.id.c5:
                setcpparms(c5, 6 * d, top + 2 * d);
                break;
            case R.id.c6:
                setcpparms(c6, 12 * d, top + 6 * d);
                break;
            case R.id.c7:
                setcpparms(c7, 2 * d, top + 8 * d);
                break;
            case R.id.c8:
                setcpparms(c8, 8 * d, top + 12 * d);
                break;
        }
    }

    public void fill1(ImageView v) {

        switch (v.getId()) {

            case R.id.t1:
                iv1.getLayoutParams().height = d;
                iv1.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams1 = (RelativeLayout.LayoutParams) iv1.getLayoutParams();
                mParams1.leftMargin = 3 * d / 2;
                mParams1.topMargin = top + 3 * d / 2;
                iv1.setLayoutParams(mParams1);
                p11 = 0;
                x1 = 1;
                updateTokenFb(TOKEN1, "1out");
                break;
            case R.id.t2:
                iv2.getLayoutParams().height = d;
                iv2.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams2 = (RelativeLayout.LayoutParams) iv2.getLayoutParams();
                mParams2.leftMargin = 2 * d + 3 * d / 2;
                mParams2.topMargin = top + 3 * d / 2;
                iv2.setLayoutParams(mParams2);
                p12 = 0;
                x2 = 1;
                updateTokenFb(TOKEN2, "2out");
                break;
            case R.id.t3:
                iv3.getLayoutParams().height = d;
                iv3.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams3 = (RelativeLayout.LayoutParams) iv3.getLayoutParams();
                mParams3.leftMargin = 3 * d / 2;
                mParams3.topMargin = 2 * d + top + 3 * d / 2;
                iv3.setLayoutParams(mParams3);
                p13 = 0;
                x3 = 1;
                updateTokenFb(TOKEN3, "3out");
                break;
            case R.id.t4:
                iv4.getLayoutParams().height = d;
                iv4.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams4 = (RelativeLayout.LayoutParams) iv4.getLayoutParams();
                mParams4.leftMargin = 2 * d + 3 * d / 2;
                mParams4.topMargin = 2 * d + top + 3 * d / 2;
                iv4.setLayoutParams(mParams4);
                p14 = 0;
                x4 = 1;
                updateTokenFb(TOKEN4, "4out");
                break;
        }

    }

    public void fill2(ImageView v) {

        switch (v.getId()) {

            case R.id.t5:
                iv5.getLayoutParams().height = d;
                iv5.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams1 = (RelativeLayout.LayoutParams) iv5.getLayoutParams();
                mParams1.leftMargin = 9 * d + 3 * d / 2;
                mParams1.topMargin = top + 3 * d / 2;
                iv5.setLayoutParams(mParams1);
                p31 = 0;
                x5 = 14;
                updateTokenFb(TOKEN5, "5out");
                break;
            case R.id.t6:
                iv6.getLayoutParams().height = d;
                iv6.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams2 = (RelativeLayout.LayoutParams) iv6.getLayoutParams();
                mParams2.leftMargin = 11 * d + 3 * d / 2;
                mParams2.topMargin = top + 3 * d / 2;
                iv6.setLayoutParams(mParams2);
                p32 = 0;
                x6 = 14;
                updateTokenFb(TOKEN6, "6out");
                break;
            case R.id.t7:
                iv7.getLayoutParams().height = d;
                iv7.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams3 = (RelativeLayout.LayoutParams) iv7.getLayoutParams();
                mParams3.leftMargin = 9 * d + 3 * d / 2;
                mParams3.topMargin = 2 * d + top + 3 * d / 2;
                iv7.setLayoutParams(mParams3);
                p33 = 0;
                x7 = 14;
                updateTokenFb(TOKEN7, "7out");
                break;
            case R.id.t8:
                iv8.getLayoutParams().height = d;
                iv8.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams4 = (RelativeLayout.LayoutParams) iv8.getLayoutParams();
                mParams4.leftMargin = 11 * d + 3 * d / 2;
                mParams4.topMargin = 2 * d + top + 3 * d / 2;
                iv8.setLayoutParams(mParams4);
                p34 = 0;
                x8 = 14;
                updateTokenFb(TOKEN8, "8out");
                break;
        }
    }

    public void fill3(ImageView v) {

        switch (v.getId()) {

            case R.id.t9:
                iv9.getLayoutParams().height = d;
                iv9.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams1 = (RelativeLayout.LayoutParams) iv9.getLayoutParams();
                mParams1.leftMargin = 9 * d + 3 * d / 2;
                mParams1.topMargin = 9 * d + top + 3 * d / 2;
                iv9.setLayoutParams(mParams1);
                p21 = 0;
                x9 = 27;
                updateTokenFb(TOKEN9, "9out");
                break;
            case R.id.t10:
                iv10.getLayoutParams().height = d;
                iv10.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams2 = (RelativeLayout.LayoutParams) iv10.getLayoutParams();
                mParams2.leftMargin = 11 * d + 3 * d / 2;
                mParams2.topMargin = 9 * d + top + 3 * d / 2;
                iv10.setLayoutParams(mParams2);
                p22 = 0;
                x10 = 27;
                updateTokenFb(TOKEN10, "10out");
                break;
            case R.id.t11:
                iv11.getLayoutParams().height = d;
                iv11.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams3 = (RelativeLayout.LayoutParams) iv11.getLayoutParams();
                mParams3.leftMargin = 9 * d + 3 * d / 2;
                mParams3.topMargin = 11 * d + top + 3 * d / 2;
                iv11.setLayoutParams(mParams3);
                p23 = 0;
                x11 = 27;
                updateTokenFb(TOKEN11, "11out");
                break;
            case R.id.t12:
                iv12.getLayoutParams().height = d;
                iv12.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams4 = (RelativeLayout.LayoutParams) iv12.getLayoutParams();
                mParams4.leftMargin = 11 * d + 3 * d / 2;
                mParams4.topMargin = 11 * d + top + 3 * d / 2;
                iv12.setLayoutParams(mParams4);
                p24 = 0;
                x12 = 27;
                updateTokenFb(TOKEN12, "12out");
                break;
        }
    }

    public void fill4(ImageView v) {

        switch (v.getId()) {
            case R.id.t13:
                iv13.getLayoutParams().height = d;
                iv13.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams1 = (RelativeLayout.LayoutParams) iv13.getLayoutParams();
                mParams1.leftMargin = 3 * d / 2;
                mParams1.topMargin = 9 * d + top + 3 * d / 2;
                iv13.setLayoutParams(mParams1);
                p41 = 0;
                x13 = 40;
                updateTokenFb(TOKEN13, "13out");
                break;
            case R.id.t14:
                iv14.getLayoutParams().height = d;
                iv14.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams2 = (RelativeLayout.LayoutParams) iv14.getLayoutParams();
                mParams2.leftMargin = 2 * d + 3 * d / 2;
                mParams2.topMargin = 9 * d + top + 3 * d / 2;
                iv14.setLayoutParams(mParams2);
                p42 = 0;
                x14 = 40;
                updateTokenFb(TOKEN14, "14out");
                break;
            case R.id.t15:
                iv15.getLayoutParams().height = d;
                iv15.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams3 = (RelativeLayout.LayoutParams) iv15.getLayoutParams();
                mParams3.leftMargin = 3 * d / 2;
                mParams3.topMargin = 11 * d + top + 3 * d / 2;
                iv15.setLayoutParams(mParams3);
                p43 = 0;
                x15 = 40;
                updateTokenFb(TOKEN15, "15out");
                break;
            case R.id.t16:
                iv16.getLayoutParams().height = d;
                iv16.getLayoutParams().width = d;
                RelativeLayout.LayoutParams mParams4 = (RelativeLayout.LayoutParams) iv16.getLayoutParams();
                mParams4.leftMargin = 2 * d + 3 * d / 2;
                mParams4.topMargin = 11 * d + top + 3 * d / 2;
                iv16.setLayoutParams(mParams4);
                p44 = 0;
                x16 = 40;
                updateTokenFb(TOKEN16, "16out");
                break;
        }
    }

}
