package tanvir.crimelogger_aust.HelperClass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ablanco.zoomy.TapListener;
import com.ablanco.zoomy.ZoomListener;
import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import tanvir.crimelogger_aust.R;

/**
 * Created by USER on 20-Nov-17.
 */

public class CustomSwipeAdapterUserCreatePost extends PagerAdapter {

    private ArrayList<Uri> images = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private Uri defaultEmptyImage;
    int num_pages;


    public CustomSwipeAdapterUserCreatePost(Context context, ArrayList<Uri> images, Uri defaultEmptyImage, int num_pages) {
        this.context = context;
        this.images = images;
        this.defaultEmptyImage = defaultEmptyImage;
        this.num_pages = num_pages;


    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);


    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {



        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View item_view = inflater.inflate(R.layout.swipelayout, view, false);




        assert item_view != null;
        final ImageView imageView =  item_view
                .findViewById(R.id.image);






        final int delPosition = position;


        if (images.get(0).equals(defaultEmptyImage)) {



            Glide.with(context).load(R.drawable.empty_image).into(imageView);
        } else if (images.size() > 0) {


            Zoomy.Builder builder = new Zoomy.Builder((Activity) context)
                    .target(imageView)
                    .enableImmersiveMode(false)
                    .animateZooming(true)
                    .tapListener(new TapListener() {
                        @Override
                        public void onTap(View v) {

                            final AlertDialog alertDialog;

                            final View imageDeleteDialogView;

                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            ;
                            imageDeleteDialogView = inflater.inflate(R.layout.delete_image, null);
                            dialogBuilder.setView(imageDeleteDialogView);
                            alertDialog = dialogBuilder.create();
                            alertDialog.show();

                            Button yesBTN = imageDeleteDialogView.findViewById(R.id.yesBTN);
                            Button noBTN = imageDeleteDialogView.findViewById(R.id.noBTN);

                            yesBTN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    alertDialog.dismiss();

                                    images.remove(position);

                                    num_pages = images.size();

                                    notifyDataSetChanged();

                                }
                            });


                            noBTN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    alertDialog.dismiss();


                                }
                            });
                        }
                    })
                    .zoomListener(new ZoomListener() {
                        @Override
                        public void onViewStartedZooming(View view) {

                        }

                        @Override
                        public void onViewEndedZooming(View view) {

                        }
                    });

            builder.register();

            Glide.with(context).load(images.get(position)).into(imageView);



        }


        view.addView(item_view, 0);
        return item_view;
    }
}
