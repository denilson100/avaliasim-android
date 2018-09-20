//package br.com.mobile10.avaliasim.util;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.app.Activity;
//import android.os.Build;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewAnimationUtils;
//
//import br.com.mobile10.avaliasim.R;
//
///**
// * Created by denmont on 10/02/2018.
// */
//
//public class AnimationsUtility extends BaseActivity {
//
//    /**
//     *
//     * @param activity Contexto para a anaimação
//     * @param view fundo dinamico que ira se movar para a anamação
//     * @param idConteudo conteudo que da tela. Apos a animação ficara escondido.
//     */
//    public static void showCircularAnimationBom(Activity activity, final View view, int idConteudo) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            int centerX = view.getRight();
//            int centerY = view.getBottom();
//            float radius = Math.max(view.getWidth(), view.getHeight()) * 2.0f;
//
//            if (view.getVisibility() == View.INVISIBLE) {
//
//                view.setBackgroundColor(activity.getResources().getColor(R.color.verde));
//                view.setVisibility(View.VISIBLE);
//                Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, radius);
//                animator.setDuration(700);
//                animator.start();
//
//            } else {
//                Log.d("TAG", "ENTROU NO ELSE");
//                Animator reveal = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, radius, 0);
//                reveal.addListener(new AnimatorListenerAdapter() {
//                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.INVISIBLE);
//
//                    }
//                });
//                reveal.start();
//                activity.findViewById(idConteudo).setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public static void showCircularAnimationRuim(Activity activity, final View view, int idConteudo) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            int centerX = view.getLeft();
//            int centerY = view.getBottom();
//            float radius = Math.max(view.getWidth(), view.getHeight()) * 2.0f;
//
//            if (view.getVisibility() == View.INVISIBLE) {
//
//                view.setBackgroundColor(activity.getResources().getColor(R.color.vermelho));
//                view.setVisibility(View.VISIBLE);
//                Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, radius);
//                animator.setDuration(700);
//                animator.start();
//
//            } else {
//                Log.d("TAG", "ENTROU NO ELSE");
//                Animator reveal = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, radius, 0);
//                reveal.addListener(new AnimatorListenerAdapter() {
//                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.INVISIBLE);
//
//                    }
//                });
//                reveal.start();
//                activity.findViewById(idConteudo).setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public static void showCircularAnimationAvaliar(Activity activity, final View view, int idConteudo) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            int centerX = view.getRight();
//            int centerY = (view.getTop() + view.getBottom()) / 3;
//            float radius = Math.max(view.getWidth(), view.getHeight()) * 2.0f;
//
//            if (view.getVisibility() == View.INVISIBLE) {
//
//                view.setBackgroundColor(activity.getResources().getColor(R.color.branco));
//                view.setVisibility(View.VISIBLE);
//                Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, radius);
//                animator.setDuration(700);
//                animator.start();
//
//            } else {
//                Log.d("TAG", "ENTROU NO ELSE");
//                Animator reveal = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, radius, 0);
//                reveal.addListener(new AnimatorListenerAdapter() {
//                    public void onAnimationEnd(Animator animation) {
//                        view.setVisibility(View.INVISIBLE);
//
//                    }
//                });
//                reveal.start();
//                activity.findViewById(idConteudo).setVisibility(View.VISIBLE);
//            }
//        }
//    }
//}
