package org.dalol.bestamharickeyboard.ui.features;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.core.models.VirtualKey;
import org.dalol.bestamharickeyboard.core.models.VirtualKeyboard;
import org.dalol.bestamharickeyboard.core.models.VirtualKeysRow;
import org.dalol.bestamharickeyboard.core.models.callback.OnVirtualKeyboardActionListener;
import org.dalol.bestamharickeyboard.core.ui.VirtualKeyboardView;
import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;
import org.dalol.bestamharickeyboard.keyboard.model.KeyboardInputActionType;

import java.util.Random;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 15:30.
 */
public class VirtualKeyboardService extends InputMethodService implements OnVirtualKeyboardActionListener {

    private static final String arr[] = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "😀", "😁", "😂", "🤣", "😃", "😄", "😅",
            "😆", "😉", "😊", "😋", "😎", "😍", "😘",
            "😗", "😙", "😚",
            "ሀ","ሁ","ሂ","ሃ","ሄ","ህ","ሆ",
            "ለ","ሉ","ሊ","ላ","ሌ","ል","ሎ","ሏ",
            "ሐ","ሑ","ሒ","ሓ","ሔ","ሕ","ሖ","ሗ"
    };

    private Context mContext;
    private AudioManager mAudioManager;
    private AdView mAdView;
    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean caps = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    public View onCreateInputView() {

        Random r = new Random();
        int bound = arr.length - 1;

        int maxCount = 10;

//        VirtualKeyboard keyboard = createKeyboard1(maxCount, r, bound);
        VirtualKeyboard keyboard = createKeyboard2(maxCount, r, bound);

        int p = CoreUtils.toPx(6f);


//        VirtualKeyboardView kv = new VirtualKeyboardView(mContext);
        View view = getLayoutInflater().inflate(R.layout.virtual_keyboard, null);

        VirtualKeyboardView kv = view.findViewById(R.id.keyboard);
        //kv.setPadding(p, p, p, p);
        kv.setVirtualKeyboard(keyboard);
        kv.setOnVirtualKeyboardActionListener(this);

        mAdView = view.findViewById(R.id.adView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                int visibility = mAdView.getVisibility();
                if (visibility != View.VISIBLE) mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                showToast("Failed to load ad -> " + i);
            }
        });
        mAdView.loadAd(new AdRequest.Builder()
                .build());

//        View view = getLayoutInflater().inflate(R.layout.layout_keyboard, null);
//        keyboardView = (KeyboardView) view.findViewById(R.id.keyboard);
//        keyboard = new Keyboard(this, R.xml.querty);
//        keyboardView.setKeyboard(keyboard);
//        keyboardView.setOnKeyboardActionListener(this);

        return view;
    }

    private VirtualKeyboard createKeyboard2(int maxCount, Random r, int bound) {
        VirtualKeyboard keyboard = new VirtualKeyboard(maxCount, 5f);


        VirtualKeysRow keysRow1 = new VirtualKeysRow(
                48f,
                VirtualKeysRow.GRAVITY_CENTER,
                0f,
                0f
        );

        for (int i = 0; i < 1; i++) {
            VirtualKey key = new VirtualKey(keysRow1);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            key.setKeyWeight(1.5f);
            keysRow1.addVirtualKey(key);
        }

        for (int i = 0; i < maxCount - 3; i++) {
            VirtualKey key = new VirtualKey(keysRow1);
            key.setKeyLabel(String.valueOf(i));
            keysRow1.addVirtualKey(key);
        }

        for (int i = 0; i < 1; i++) {
            VirtualKey key = new VirtualKey(keysRow1);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            key.setKeyWeight(1.5f);
            keysRow1.addVirtualKey(key);
        }

        VirtualKeysRow keysRow2 = VirtualKeysRow.withRowHeight(
                42f
        );

        for (int i = 0; i < maxCount; i++) {
            VirtualKey key = new VirtualKey(keysRow2);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
//            key.setKeyLabel(String.valueOf(++counter));
            keysRow2.addVirtualKey(key);
        }

        VirtualKeysRow keysRow3 = new VirtualKeysRow(
                32f, VirtualKeysRow.GRAVITY_SPREAD,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN
        );

        for (int i = 0; i < 2; i++) {
            VirtualKey key = new VirtualKey(keysRow3);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            key.setKeyWeight(1.5f);
            keysRow3.addVirtualKey(key);
        }

        for (int i = 0; i < 6; i++) {
            VirtualKey key = new VirtualKey(keysRow3);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            keysRow3.addVirtualKey(key);
        }

        VirtualKeysRow keysRow4 = new VirtualKeysRow(
                32f, VirtualKeysRow.GRAVITY_CENTER,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN
        );

        for (int i = 0; i < 8; i++) {
            VirtualKey key = new VirtualKey(keysRow4);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
//            key.setKeyLabel(String.valueOf(++counter));
            keysRow4.addVirtualKey(key);
        }

        VirtualKeysRow keysRow5 = new VirtualKeysRow(
                36f, VirtualKeysRow.GRAVITY_SPREAD,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN
        );

        for (int i = 0; i < 2; i++) {
            VirtualKey key = new VirtualKey(keysRow5);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
//            key.setKeyLabel(String.valueOf(++counter));
            keysRow5.addVirtualKey(key);
        }

        VirtualKey key22 = new VirtualKey(keysRow5);
        key22.setKeyLabel(arr[(r.nextInt(bound))]);
        key22.setKeyWeight(2.5f);
//            key.setKeyLabel(String.valueOf(++counter));
        keysRow5.addVirtualKey(key22);

        for (int i = 0; i < 2; i++) {
            VirtualKey key = new VirtualKey(keysRow5);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
//            key.setKeyLabel(String.valueOf(++counter));
            keysRow5.addVirtualKey(key);
        }

        VirtualKeysRow keysRow6 = new VirtualKeysRow(
                48f, VirtualKeysRow.GRAVITY_END,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN,
                VirtualKeysRow.DEFAULT_KEYS_MARGIN
        );
        for (int i = 0; i < 8; i++) {
            VirtualKey key = new VirtualKey(keysRow6);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
//            key.setKeyLabel(String.valueOf(++counter));
            keysRow6.addVirtualKey(key);
        }

        keyboard.addVirtualKeysRow(keysRow1);
        keyboard.addVirtualKeysRow(keysRow2);

        keyboard.addVirtualKeysRow(keysRow1);
        keyboard.addVirtualKeysRow(keysRow2);

        keyboard.addVirtualKeysRow(keysRow1);
//        keyboard.addVirtualKeysRow(keysRow2);


//        keyboard.addVirtualKeysRow(keysRow3);
//        keyboard.addVirtualKeysRow(keysRow4);
//        keyboard.addVirtualKeysRow(keysRow5);
//        keyboard.addVirtualKeysRow(keysRow6);
//        keyboard.addVirtualKeysRow(keysRow1);
//        keyboard.addVirtualKeysRow(keysRow1);
//        keyboard.addVirtualKeysRow(keysRow3);
//        keyboard.addVirtualKeysRow(keysRow1);

        return keyboard;
    }

    private VirtualKeyboard createKeyboard1(int maxCount, Random r, int bound) {

        VirtualKeyboard keyboard = new VirtualKeyboard(maxCount, 8f);

        VirtualKeysRow keysRow1 = VirtualKeysRow.withRowHeight(32f);

        for (int i = 0; i < maxCount; i++) {
            VirtualKey key = new VirtualKey(keysRow1);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            keysRow1.addVirtualKey(key);
        }

        keyboard.addVirtualKeysRow(keysRow1);

        VirtualKeysRow keysRow2 = new VirtualKeysRow();

        for (int j = 0; j < maxCount - 1; j++) {
            VirtualKey key = new VirtualKey(keysRow2);
            if (j == 4) key.setKeyWeight(1.5f);
            if (j == 5) key.setKeyWeight(1.5f);
            key.setKeyLabel(arr[(r.nextInt(bound))]);
            keysRow2.addVirtualKey(key);
        }

        keyboard.addVirtualKeysRow(keysRow2);

        for (int i = 0; i < 4; i++) {
            VirtualKeysRow keysRow = VirtualKeysRow.withRowHeight(40f);

            for (int j = 0; j < maxCount; j++) {
                VirtualKey key = new VirtualKey(keysRow);
                key.setKeyLabel(arr[(r.nextInt(bound))]);
                keysRow.addVirtualKey(key);
            }

            keyboard.addVirtualKeysRow(keysRow);
        }
        return keyboard;
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public void onWindowShown() {
        super.onWindowShown();

        final EditorInfo ei = getCurrentInputEditorInfo();
        int imeOptions = ei.imeOptions;

        KeyboardInputActionType actionType = KeyboardInputActionType.fromeImeOptions(imeOptions);
        showToast("Action onWindowShown() -> " + actionType);
    }

    @Override
    public void onPress(String key) {
        //showToast("onPress() -> " + key);
    }

    @Override
    public void onKey(int primaryCode, String key) {

        //playSound(primaryCode);

        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                default:
                    inputConnection.commitText(key, 1);
            }
        }
    }

    @Override
    public void onRelease(String key) {
        showToast("onRelease() -> " + key);
    }

    private void playSound(int keyCode) {

        //v.vibrate(20);

        switch (keyCode) {
            case 32:
                mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                mAudioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    private void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
