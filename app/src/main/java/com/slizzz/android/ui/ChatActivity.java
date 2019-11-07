package com.slizzz.android.ui;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slizzz.android.R;
import com.slizzz.android.adapter.ChatDetailsAdapter;
import com.slizzz.android.model.ChatDetails;
import com.slizzz.android.model.Event;
import com.slizzz.android.util.LocalManager;
import com.slizzz.android.util.UiUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sjena on 26/10/18.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private TextView header, eventName;
    private ParseUser withChatUser;
    private ImageView profile_image, sent;
    private EditText chatEnterEdtTv;
    ChatDetailsAdapter chatDetailsAdapter;
    List<ParseObject> chatDataList = new ArrayList<>();
    private RecyclerView chatRecycler;
    List<String> idList = new ArrayList<>();
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        withChatUser = LocalManager.getInstance().getParseUser();
        event = LocalManager.getInstance().getSelectedEvent();
        idList.add(event.getObjectId()+"/"+ParseUser.getCurrentUser().getObjectId()+"/"+withChatUser.getObjectId());
        idList.add(event.getObjectId()+"/"+withChatUser.getObjectId()+"/"+ParseUser.getCurrentUser().getObjectId());

        setContentView(R.layout.activity_chat);



        setToolBar();
        inItUi();
        getChatList();


    }

    private void getChatList() {




        ParseQuery<ParseObject> query = ParseQuery.getQuery("SZChatList");
        query.whereContainedIn("groupID",idList);
        query.orderByAscending("createdAt");


        query.findInBackground(new FindCallback<ParseObject>() {
            int started = 1;

            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    UiUtil.cancelProgressDialogue();


                    chatDetailsAdapter.notifyDayta(list);
                    chatRecycler.scrollToPosition(list.size() - 1);

                    //inItUi();
                } else
                {

                }
            }
        });
    }

    private void inItUi() {
        profile_image = (ImageView)findViewById(R.id.profile_image);
        if(withChatUser.getParseFile("profilePicture") != null) {
            byte[] bitmapdata = new byte[0];
            try {
                bitmapdata = withChatUser.getParseFile("profilePicture").getData();
            } catch (ParseException e) {
                e.printStackTrace();
            }
           profile_image.setImageBitmap(BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length));
        }

        sent = (ImageView)findViewById(R.id.sent);
        sent.setOnClickListener(this);

        chatEnterEdtTv = (EditText) findViewById(R.id.chatEnterEdtTv);

        chatRecycler = (RecyclerView) findViewById(R.id.chatRecycler);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        chatDetailsAdapter = new ChatDetailsAdapter(this,chatDataList );
        chatRecycler.setAdapter(chatDetailsAdapter);
    }


    private void setToolBar() {
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        header = (TextView)findViewById(R.id.header);
        header.setText(withChatUser.getString("firstName")+" "+withChatUser.getString("lastName"));



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }



    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id)
        {
            case R.id.sent:

                if(chatEnterEdtTv.getText().toString().length() == 0)
                {
                    UiUtil.showToast(this,"Please enter valid message");
                    return;
                }
                sendMessageToDb();

                chatEnterEdtTv.setText("");
                getChatList();

                break;
        }

    }

    private void sendMessageToDb() {


        ParseObject profilePicture = ParseObject.create("SZChatList");
        //UiUtil.showProgressDialogue(this, "","Creating Event");
        profilePicture.put("groupID", event.getObjectId()+"/"+ParseUser.getCurrentUser().getObjectId()+"/"+withChatUser.getObjectId());

        profilePicture.put("receiver", withChatUser.getObjectId());
        profilePicture.put("sender", ParseUser.getCurrentUser().getObjectId());
        profilePicture.put("lastMessage", chatEnterEdtTv.getText().toString());
        profilePicture.put("messageType", "TEXT");
        profilePicture.put("timeStamp", new Date());

        ParseACL acl=new ParseACL();
        acl.setWriteAccess(ParseUser.getCurrentUser(), true);

        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        profilePicture.put("isPublic", true);

        profilePicture.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if (e == null)
                {
                    Log.i("Parse", "saved successfully");
                    //UiUtil.showToast(Chat.this,"Event create successfully");

                    //finish();

                }
                else
                {
                    Log.i("Parse", "error while saving");
                }

                UiUtil.cancelProgressDialogue();
            }
        });
    }
}
