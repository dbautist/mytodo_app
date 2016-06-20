package com.codepath.todoapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.todoapplication.R;
import com.codepath.todoapplication.model.TodoItem;
import com.codepath.todoapplication.utils.TDUtil;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoItem> {
  public TodoAdapter(Context context, List<TodoItem> users) {
    super(context, R.layout.item_todo, users);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View view = convertView;
    ViewHolder viewHolder;

    // Check if an existing view is being reused, otherwise inflate the view
    if (view == null) {
      LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      view = inflater.inflate(R.layout.item_todo, parent, false);

      // configure view holder
      viewHolder = new ViewHolder();
      viewHolder.title = (TextView) view.findViewById(R.id.titleText);
      viewHolder.date = (TextView) view.findViewById(R.id.dateText);
      viewHolder.check = (ImageView) view.findViewById(R.id.checkImage);
      viewHolder.priority = (TextView) view.findViewById(R.id.priorityText);
      view.setTag(viewHolder);
    }

    ViewHolder holder = (ViewHolder) view.getTag();
    TodoItem item = getItem(position);
    if (item != null) {
      holder.title.setText(item.title);
      holder.date.setText(TDUtil.convertMillisToDateString(item.dateInMilliseconds));
      if (item.status.toLowerCase().equals("done"))
        holder.check.setVisibility(View.VISIBLE);
      else
        holder.check.setVisibility(View.INVISIBLE);

      String priority = item.priority.toLowerCase();
      if (priority != null) {
        holder.priority.setText(item.priority);
        if (priority.equals("high"))
          holder.priority.setTextColor(view.getResources().getColor(R.color.red));
        else if (priority.equals("medium"))
          holder.priority.setTextColor(view.getResources().getColor(R.color.orange));
        else
          holder.priority.setTextColor(view.getResources().getColor(R.color.green));
      }
    }

    return view;
  }

  private static class ViewHolder {
    TextView title;
    TextView date;
    TextView priority;
    ImageView check;
  }
}
