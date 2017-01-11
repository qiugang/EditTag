package me.originqiu.edittag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.originqiu.library.EditTag;

public class MainActivity extends AppCompatActivity
        implements SwitchCompat.OnCheckedChangeListener {

    private EditTag editTagView;

    private SwitchCompat statusSwitchView;

    private List<String> tagStrings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTagView = (EditTag) findViewById(R.id.edit_tag_view);
        statusSwitchView = (SwitchCompat) findViewById(R.id.status_switch);
        statusSwitchView.setOnCheckedChangeListener(this);

        for (int i = 0; i < 10; i++) {
            tagStrings.add("test" + i);
        }
        //Set tag add callback before set tag list
        editTagView.setTagAddCallBack(new EditTag.TagAddCallback() {
            @Override
            public boolean onTagAdd(String tagValue) {
                if ("test1".equals(tagValue)) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        editTagView.setTagDeletedCallback(new EditTag.TagDeletedCallback() {
            @Override
            public void onTagDelete(String deletedTagValue) {
                Toast.makeText(MainActivity.this, deletedTagValue, Toast.LENGTH_SHORT).show();
            }
        });
        editTagView.setTagList(tagStrings);

        editTagView.addTag("hello world!");
        editTagView.removeTag("test3");

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        editTagView.setEditable(isChecked);
    }
}
