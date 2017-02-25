package com.tf.bluetoothmessenger.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tf.bluetoothmessenger.R;
import com.tf.bluetoothmessenger.data.BluetoothManager;
import com.tf.bluetoothmessenger.model.BTDevice;
import com.tf.bluetoothmessenger.model.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment implements BluetoothManager.OnMessageReceivedListener {

    private static final String ARG_BT_DEVICE = "btDevice";
    private MessageAdapter mAdapter;

    private BluetoothManager mBluetoothManager;
    private Parcelable mBTDevice;

    public static ConversationFragment getInstance(BTDevice device) {
        final ConversationFragment fragment = new ConversationFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_BT_DEVICE, device);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_BT_DEVICE)) {
            mBTDevice = getArguments().getParcelable(ARG_BT_DEVICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listMessages = (ListView) view.findViewById(R.id.list_messages);

        mAdapter = new MessageAdapter(getActivity());
        listMessages.setAdapter(mAdapter);

        mBluetoothManager = BluetoothManager.getInstance(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        mBluetoothManager.addOnMessageReceivedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mBluetoothManager.removeOnMessageReceivedListener(this);
    }

    @Override
    public void onReceived(String message) {

    }

    private class MessageAdapter extends ArrayAdapter<Message> {

        List<Message> items = new ArrayList<>();

        public MessageAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public void add(Message message) {
            items.add(message);
        }

        @Override
        public void addAll(Collection<? extends Message> collection) {
            items.addAll(collection);
        }

        @Override
        public void addAll(Message... items) {
            this.items.addAll(Arrays.asList(items));
        }

        @Override
        public void remove(Message message) {
            items.remove(message);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Message getItem(int position) {
            return items.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.two_line_list_item, parent, false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position).getSender().getName());
            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position).getMessage());

            return convertView;
        }
    }
}
