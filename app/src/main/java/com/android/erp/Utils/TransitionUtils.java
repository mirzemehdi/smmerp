package com.android.erp.Utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class TransitionUtils {
    public static void replaceFragmentInActivityRight(@NonNull FragmentManager fragmentManager,
                                                      @NonNull Fragment fragment, String fragmentTag, int frameId,
                                                      boolean isAddtoBackTrace) {
        if (fragmentManager.findFragmentByTag(fragmentTag) != null)
            return;

        if (fragmentManager == null)

            if (fragment == null){

            }


        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments().size() > 0)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.setCustomAnimations(R.anim.slide_in_detail,R.anim.slide_out_detail);
        //transaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);

        if (isAddtoBackTrace)
            transaction.addToBackStack(null);
        transaction.add(frameId, fragment, fragmentTag);
        transaction.commit();

    }
}
