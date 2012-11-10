package bma.groomservice.tts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

public class Speaker implements OnInitListener {

	private static Logger logger = LoggerFactory.getLogger(Speaker.class);
	public static final int MY_DATA_CHECK_CODE = 1;

	Activity activity;
	String sentence;

	public Speaker(Activity activity) {
		this.activity = activity;
	}

	public void say(String sentence) {
		logger.debug("say");
		this.sentence = sentence;
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		activity.startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);
	}

	private TextToSpeech mTts;

	@Override
	public void onInit(int status) {
		logger.debug("onInit({})", status);
		mTts.speak(sentence, TextToSpeech.QUEUE_ADD, null);
	}
}
