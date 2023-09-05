import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.Random;

public class ButtonAdapter extends BaseAdapter {
    private Context context;
    private Random random = new Random();

    public ButtonAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            button.setPadding(8, 8, 8, 8);
        } else {
            button = (Button) convertView;
        }

        int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        button.setBackgroundColor(color);

        return button;
    }
}
