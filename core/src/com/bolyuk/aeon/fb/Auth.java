package com.bolyuk.aeon.fb;

import com.bolyuk.aeon.interfaces.Action;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class Auth {

    public static void login(final Action onLogged){
        if(GdxFIRAuth.inst().getCurrentUser() == null)
            GdxFIRAuth.inst().google().signIn().fail(new BiConsumer<String, Throwable>() {
                @Override
                public void accept(String s, Throwable throwable) {
                    //System.out.println("\n\n"+s+"\n\n");
                    login(onLogged);
                }
            }).then(new Consumer<GdxFirebaseUser>() {
                @Override
                public void accept(GdxFirebaseUser gdxFirebaseUser) {
                    //System.out.println("\n\n\n\n\n user is logged \n\n\n\n\n");
                    onLogged.aNotify();
                }
            });
        else {
            //System.out.println("\n\n\n\n\n user is logged \n\n\n\n\n");
            onLogged.aNotify();
        }

    }

    public static String getUid(){
        return GdxFIRAuth.inst().getCurrentUser().getUserInfo().getUid();
    }
}
