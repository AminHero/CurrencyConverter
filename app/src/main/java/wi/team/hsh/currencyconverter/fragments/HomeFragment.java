package wi.team.hsh.currencyconverter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

import wi.team.hsh.currencyconverter.R;

/**
 * Created by amin on 31.10.17.
 */

public class HomeFragment extends Fragment{

    ImageView bitcoin,bitcoin_cash,cardano,dash,ethereum,ethereum_classic, iota,lisk,litecoin,monero,nem,neo, omnisego, qtum, ripple;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,false);

        //TODO DOKU

        dash = (ImageView) rootView.findViewById(R.id.dash);
        cardano = (ImageView) rootView.findViewById(R.id.cardano);
        neo= (ImageView) rootView.findViewById(R.id.neo);
        qtum = (ImageView) rootView.findViewById(R.id.qtum);
        ripple = (ImageView) rootView.findViewById(R.id.ripple);

        iota = (ImageView) rootView.findViewById(R.id.iota);
        lisk = (ImageView) rootView.findViewById(R.id.lisk);
        monero = (ImageView) rootView.findViewById(R.id.monero);
        nem= (ImageView) rootView.findViewById(R.id.nem);
        omnisego = (ImageView) rootView.findViewById(R.id.omisego);




        //ANIMATION
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        //Setup anim with desired properties
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.RELATIVE_TO_PARENT); //Repeat animation indefinitely
        rotateAnimation.setDuration(700); //Put desired duration per anim cycle here, in milliseconds


        TranslateAnimation animateLeft = new TranslateAnimation(0.0f, 400.0f,
                0.0f, 100.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animateLeft.setDuration(15000);  // animation duration
        animateLeft.setRepeatCount(5);  // animation repeat count
        animateLeft.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);

        TranslateAnimation animateRight = new TranslateAnimation(400.0f, 0.0f,
                0.0f, 100.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animateRight.setDuration(15000);  // animation duration
        animateRight.setRepeatCount(5);  // animation repeat count
        animateRight.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);


        //Start animation
        dash.startAnimation(animateRight);
        cardano.startAnimation(animateRight);
        qtum.startAnimation(animateRight);
        neo.startAnimation(animateRight);
        ripple.startAnimation(animateRight);


        iota.startAnimation(animateLeft);
        lisk.startAnimation(animateLeft);
        monero.startAnimation(animateLeft);
        nem.startAnimation(animateLeft);
        omnisego.startAnimation(animateLeft);

        //Later on, use view.setAnimation(null) to stop it.


        return rootView;
    }
}
