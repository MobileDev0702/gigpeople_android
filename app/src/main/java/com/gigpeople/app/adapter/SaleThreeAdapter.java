package com.gigpeople.app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.SalesDeliveryActivity;
import com.gigpeople.app.model.ChatModel;

import java.util.List;

public class SaleThreeAdapter extends RecyclerView.Adapter<SaleThreeAdapter.DataObjectHolder> {


    Context context;
    List<ChatModel> chat_list;
    String status="0";
    Dialog rating,reviewdialog,messagesialog,canceldialog,disputedialog;
    ImageView fivestar,img_4star,img_3star,img_2star,img_1star;
    LinearLayout lin_5star,lin_4star,lin_3star,lin_2star,lin_1star;
    public interface CallBack{
        public void call(String name);
    }


    public SaleThreeAdapter(Context context, List<ChatModel> chat_list) {

        this.context = context;
        this.chat_list = chat_list;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder{


        TextView name;
        LinearLayout layout2;
        RelativeLayout layout1;
        RelativeLayout rock;
        LinearLayout layout,layout3;
        Button accept,reject;
        ImageView review,revision,message,cancel,dispute,userreview;
        RelativeLayout relativeLayout;
        public DataObjectHolder(View itemView){
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.txt_name);
            layout1=(RelativeLayout)itemView.findViewById(R.id.relative1);
            layout2=(LinearLayout)itemView.findViewById(R.id.layout2);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relative2);

           /* rock = (RelativeLayout) itemView.findViewById(R.id.relative2);
            layout=(LinearLayout)itemView.findViewById(R.id.buttonlayout);
            accept = (Button) itemView.findViewById(R.id.btn_accept);
            reject = (Button)itemView.findViewById(R.id.btn_decline);*/
            layout3=(LinearLayout)itemView.findViewById(R.id.layout3);
            review=(ImageView)itemView.findViewById(R.id.review);
            revision=(ImageView)itemView.findViewById(R.id.revision);
            message=(ImageView)itemView.findViewById(R.id.message);
            cancel=(ImageView)itemView.findViewById(R.id.cancel);
            dispute=(ImageView)itemView.findViewById(R.id.dispute);
            userreview=(ImageView)itemView.findViewById(R.id.userreview);

        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_mysale_orders_three, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.name.setText(chat_list.get(position).getName());

        if(position==2)
        {
            holder.layout1.setVisibility(View.VISIBLE);
            holder.layout2.setVisibility(View.GONE);
           holder.relativeLayout.setVisibility(View.GONE);


            holder.layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,SalesDeliveryActivity.class);
                    context.startActivity(intent);
                }
            });

        }
        else if(position==1)
        {
           holder.relativeLayout.setVisibility(View.GONE);

        }
        else if(position==3)
        {
          holder.relativeLayout.setVisibility(View.GONE);

        }

else if(position==4)
        {
            holder.layout1.setVisibility(View.GONE);
            holder.layout2.setVisibility(View.GONE);
         holder.relativeLayout.setVisibility(View.GONE);
            //holder.layout.setVisibility(View.VISIBLE);
            holder.layout3.setVisibility(View.VISIBLE);

            holder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    messagesialog = new Dialog(context);
                    messagesialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    messagesialog.setContentView(R.layout.dialog_sales_message);
                    messagesialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    messagesialog.show();
                    ImageView close=(ImageView)messagesialog.findViewById(R.id.img_close);
                    Button submit=(Button) messagesialog.findViewById(R.id.btn_submit);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            messagesialog.dismiss();
                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          /*  Intent intent1=new Intent(context,MainActivity.class);
                            intent1.putExtra("page","1");
                            context.startActivity(intent1);*/
                          messagesialog.dismiss();
                        }
                    });

                }
            });

holder.review.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        rating = new Dialog(context);
        rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rating.setContentView(R.layout.dialog_rating);
        rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rating.show();
        ImageView close=(ImageView)rating.findViewById(R.id.img_close);
        Button submit=(Button) rating.findViewById(R.id.btn_submit);
        fivestar = (ImageView) rating.findViewById(R.id.fivestar);
        img_4star = (ImageView) rating.findViewById(R.id.img_4star);
        img_3star = (ImageView) rating.findViewById(R.id.img_3star);
        img_2star = (ImageView) rating.findViewById(R.id.img_2star);
        img_1star = (ImageView) rating.findViewById(R.id.img_onestar);
        lin_5star = (LinearLayout) rating.findViewById(R.id.lin_5star);
        lin_4star = (LinearLayout) rating.findViewById(R.id.lin_4star);
        lin_3star = (LinearLayout) rating.findViewById(R.id.lin_3star);
        lin_2star = (LinearLayout) rating.findViewById(R.id.lin_2star);
        lin_1star = (LinearLayout) rating.findViewById(R.id.lin_1star);

        lin_5star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.five_star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);

            }
        });
        lin_4star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.four_star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);


            }
        });
        lin_3star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.three_star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.gray_1star);


            }
        });
        lin_2star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.two_star);
                img_1star.setImageResource(R.drawable.gray_1star);


            }
        });
        lin_1star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fivestar.setImageResource(R.drawable.gray_5star);
                img_4star.setImageResource(R.drawable.gray_4star);
                img_3star.setImageResource(R.drawable.gray_3star);
                img_2star.setImageResource(R.drawable.gray_2star);
                img_1star.setImageResource(R.drawable.one_star);


            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent1=new Intent(context,MainActivity.class);
                intent1.putExtra("page","1");
                context.startActivity(intent1);*/
                rating.dismiss();
            }
        });
    }
});
            holder.userreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rating = new Dialog(context);
                    rating.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    rating.setContentView(R.layout.dialog_userrating);
                    rating.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    rating.show();
                    ImageView close=(ImageView)rating.findViewById(R.id.img_close);
                    Button submit=(Button) rating.findViewById(R.id.btn_submit);
                    fivestar = (ImageView) rating.findViewById(R.id.fivestar);
                    img_4star = (ImageView) rating.findViewById(R.id.img_4star);
                    img_3star = (ImageView) rating.findViewById(R.id.img_3star);
                    img_2star = (ImageView) rating.findViewById(R.id.img_2star);
                    img_1star = (ImageView) rating.findViewById(R.id.img_onestar);
                    lin_5star = (LinearLayout) rating.findViewById(R.id.lin_5star);
                    lin_4star = (LinearLayout) rating.findViewById(R.id.lin_4star);
                    lin_3star = (LinearLayout) rating.findViewById(R.id.lin_3star);
                    lin_2star = (LinearLayout) rating.findViewById(R.id.lin_2star);
                    lin_1star = (LinearLayout) rating.findViewById(R.id.lin_1star);

                    lin_5star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fivestar.setImageResource(R.drawable.five_star);
                            img_4star.setImageResource(R.drawable.gray_4star);
                            img_3star.setImageResource(R.drawable.gray_3star);
                            img_2star.setImageResource(R.drawable.gray_2star);
                            img_1star.setImageResource(R.drawable.gray_1star);

                        }
                    });
                    lin_4star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fivestar.setImageResource(R.drawable.gray_5star);
                            img_4star.setImageResource(R.drawable.four_star);
                            img_3star.setImageResource(R.drawable.gray_3star);
                            img_2star.setImageResource(R.drawable.gray_2star);
                            img_1star.setImageResource(R.drawable.gray_1star);


                        }
                    });
                    lin_3star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fivestar.setImageResource(R.drawable.gray_5star);
                            img_4star.setImageResource(R.drawable.gray_4star);
                            img_3star.setImageResource(R.drawable.three_star);
                            img_2star.setImageResource(R.drawable.gray_2star);
                            img_1star.setImageResource(R.drawable.gray_1star);


                        }
                    });
                    lin_2star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fivestar.setImageResource(R.drawable.gray_5star);
                            img_4star.setImageResource(R.drawable.gray_4star);
                            img_3star.setImageResource(R.drawable.gray_3star);
                            img_2star.setImageResource(R.drawable.two_star);
                            img_1star.setImageResource(R.drawable.gray_1star);


                        }
                    });
                    lin_1star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fivestar.setImageResource(R.drawable.gray_5star);
                            img_4star.setImageResource(R.drawable.gray_4star);
                            img_3star.setImageResource(R.drawable.gray_3star);
                            img_2star.setImageResource(R.drawable.gray_2star);
                            img_1star.setImageResource(R.drawable.one_star);


                        }
                    });

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rating.dismiss();
                        }
                    });

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* Intent intent1=new Intent(context,MainActivity.class);
                            intent1.putExtra("page","1");
                            context.startActivity(intent1);*/
                           rating.dismiss();
                        }
                    });
                }
            });

holder.revision.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        reviewdialog = new Dialog(context);
        reviewdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewdialog.setContentView(R.layout.dialog_sales_revision);
        reviewdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        reviewdialog.show();
        ImageView close=(ImageView)reviewdialog.findViewById(R.id.img_close);
        Button submit=(Button) reviewdialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewdialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent1=new Intent(context,MainActivity.class);
                intent1.putExtra("page","2");
                context.startActivity(intent1);*/
               reviewdialog.dismiss();
            }
        });
    }
});


holder.cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        canceldialog = new Dialog(context);
        canceldialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        canceldialog.setContentView(R.layout.dialog_order_cancel);
        canceldialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        canceldialog.show();
        ImageView close = (ImageView) canceldialog.findViewById(R.id.img_close);
        Button submit = (Button) canceldialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceldialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("page", "4");
                context.startActivity(intent1);*/
               canceldialog.dismiss();
            }
        });

    }
});

holder.dispute.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        disputedialog = new Dialog(context);
        disputedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        disputedialog.setContentView(R.layout.dialog_dispute);
        disputedialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        disputedialog.show();
        ImageView close = (ImageView) disputedialog.findViewById(R.id.img_close);
        Button submit = (Button) disputedialog.findViewById(R.id.btn_submit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disputedialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("page", "4");
                context.startActivity(intent1);*/
               disputedialog.dismiss();
            }
        });


    }
});
        }





        else if(position==0)
        {
            //holder.rock.setVisibility(View.VISIBLE);
        }

/*

holder.accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(context,"Your revision was accepted succesfully",Toast.LENGTH_SHORT).show();
    }
});
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("page", "3");
                context.startActivity(intent1);

            }
        });
*/


    }


    @Override
    public int getItemCount() {
        return chat_list.size();
    }


}
