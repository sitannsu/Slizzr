package com.slizzz.android.util;


import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import com.slizzz.android.ui.DashboardActivity;


/**
 * Created by Barsa on 11/2/2016.
 */
public class AddFragments {

    public static void addFragmentToDrawerActivity(Context context, Bundle bundle, Class<? extends Fragment> fragmentClass)
    {
        if(context instanceof DashboardActivity)
        {
           // AddFragments.addFragmentActivityTotack(context, bundle, fragmentClass);
            AddFragments.addFragmentActivity(context, bundle, fragmentClass);
        }
    }

    public static void addFragmentToStack(Context context, Bundle bundle, Class<? extends Fragment> fragmentClass)
    {
        if(context instanceof  DashboardActivity)
        {
            AddFragments.addFragmentActivityTotack(context, bundle, fragmentClass);
            //AddFragments.addFragmentActivity(context, bundle, fragmentClass);
        }
    }

    private static void addFragmentActivityTotack(Context context, Bundle bundle, Class<? extends Fragment> fragmentClass) {
        if(context instanceof DashboardActivity)
        {
            ((DashboardActivity)context).addFragmentToStack(Fragment.instantiate(context, fragmentClass.getName(), bundle));
        }
    }

    private static void addFragmentActivity(Context context, Bundle bundle, Class<? extends Fragment> fragmentClass) {
        if(context instanceof DashboardActivity)
        {
            //((BuyItemActivity)context).addFragmentToStack(Fragment.instantiate(context, fragmentClass.getName(), bundle));

            ((DashboardActivity)context).addFragmentNotToStack(Fragment.instantiate(context, fragmentClass.getName(), bundle));

        }
    }




}
