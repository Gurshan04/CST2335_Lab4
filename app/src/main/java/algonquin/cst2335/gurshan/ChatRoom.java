package algonquin.cst2335.gurshan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import algonquin.cst2335.gurshan.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Connect to ViewModel
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        if (chatModel.messages.getValue() == null) {
            chatModel.messages.setValue(new ArrayList<>());
        }
        messages = chatModel.messages.getValue();

        // RecyclerView Setup
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new RecyclerView.Adapter<>() {

            @Override
            public int getItemViewType(int position) {
                return messages.get(position).isSent() ? 0 : 1;
            }

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                int layout = (viewType == 0) ? R.layout.sent_message : R.layout.receive_message;
                View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
                return new MyRowHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chat = messages.get(position);
                holder.messageText.setText(chat.getMessage());
                holder.timeText.setText(chat.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
        };

        binding.recycleView.setAdapter(myAdapter);

        // Send Button
        binding.sendButton.setOnClickListener(v -> {
            String typedMessage = binding.messageInput.getText().toString().trim();
            if (!typedMessage.isEmpty()) {
                String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
                        .format(new Date());

                ChatMessage newMessage = new ChatMessage(typedMessage, time, true);
                messages.add(newMessage);
                chatModel.messages.setValue(messages);
                myAdapter.notifyItemInserted(messages.size() - 1);
                binding.recycleView.scrollToPosition(messages.size() - 1);
                binding.messageInput.setText("");
            }
        });

        // Receive Button
        binding.receiveButton.setOnClickListener(v -> {
            String typedMessage = binding.messageInput.getText().toString().trim();
            if (!typedMessage.isEmpty()) {
                String time = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
                        .format(new Date());

                ChatMessage newMessage = new ChatMessage(typedMessage, time, false);
                messages.add(newMessage);
                chatModel.messages.setValue(messages);
                myAdapter.notifyItemInserted(messages.size() - 1);
                binding.recycleView.scrollToPosition(messages.size() - 1);
                binding.messageInput.setText("");
            }
        });
    }

    // ViewHolder
    static class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}
