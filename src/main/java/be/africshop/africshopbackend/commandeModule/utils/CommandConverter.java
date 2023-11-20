package be.africshop.africshopbackend.commandeModule.utils;


import be.africshop.africshopbackend.commandeModule.dto.CommandRequest;
import be.africshop.africshopbackend.commandeModule.entities.Command;
import be.africshop.africshopbackend.commandeModule.response.CommandResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandConverter {

    public Command CommandeRequestToObject(CommandRequest request) {
        Command Command = new Command();
        BeanUtils.copyProperties(request, Command);
        return Command;
    }


    public Command CommandeRequestToObject(CommandRequest request, Command Command) {
        BeanUtils.copyProperties(request, Command);
        return Command;
    }

    public CommandResponse objectToResponse(Command request){
        CommandResponse response = new CommandResponse();
        BeanUtils.copyProperties(request, response);
        return response;
    }
}
