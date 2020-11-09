package com.klitwynenko.shoutbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String shoutbox(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "index";
    }

    @PostMapping("/addmessage")
    public String addNewMessage(@ModelAttribute Message message, Model model) {
        if(message.getMessageText().isEmpty() || message.getUserName().isEmpty())
            return "redirect:/";

        message.setDate(new Date());

        messageRepository.save(message);
        model.addAttribute("messages", messageRepository.findAll());

        return "redirect:/";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Message> getAllMessages() {
        // return JSON with all messages
        return messageRepository.findAll();
    }

    @GetMapping("/editmessage/{id}")
    public String displayEditForm(@PathVariable("id") int id, Model model) {
        Optional<Message> message = messageRepository.findById(id);
        if(!message.isPresent())
            return "redirect:/";

        model.addAttribute("message", message.get());
        return "edit-message";
    }

    @PostMapping("/updatemessage/{id}")
    public String updateMessage(@PathVariable("id") int id, @ModelAttribute Message message, Model model, @RequestParam String dateToParse) {
        if(message.getMessageText().isEmpty() || message.getUserName().isEmpty() || dateToParse.isEmpty())
            return "redirect:/";

        Optional<Message> foundMessage = messageRepository.findById(id);
        if(foundMessage.isPresent()) {
            foundMessage.get().setMessageText(message.getMessageText());
            foundMessage.get().setUserName(message.getUserName());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                foundMessage.get().setDate(format.parse(dateToParse));
            } catch (ParseException e) {
                e.printStackTrace();
                return "redirect:/";
            }

            messageRepository.save(foundMessage.get());
            model.addAttribute("messages", messageRepository.findAll());
        }

        return "redirect:/";
    }

    @GetMapping("/deletemessage/{id}")
    public String deleteMessage(@PathVariable("id") int id, Model model) {
        Optional<Message> foundMessage = messageRepository.findById(id);
        if(foundMessage.isPresent()) {
            messageRepository.delete(foundMessage.get());
            model.addAttribute("messages", messageRepository.findAll());
        }

        return "redirect:/";
    }

}
