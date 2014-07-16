package sourcecoded.beheaded.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import sourcecoded.beheaded.utils.EntityHelper;
import sourcecoded.beheaded.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class GiveMobhead extends CommandBase {
    @Override
    public String getCommandName() {
        return "head";
    }

    @Override
    public String getCommandUsage(ICommandSender var1) {
        return "/head <headtype>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1)
            throw new WrongUsageException(getCommandUsage(sender), new Object[0]);

        if (!EntityHelper.living.containsKey(args[0]))
            throw new WrongUsageException("That head was not found!", new Object[0]);

        ItemUtils.dropSkull(args[0], (EntityPlayer) sender);
    }

    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        ArrayList list = new ArrayList();
        list.addAll(EntityHelper.living.keySet());
        return list;
    }
}
