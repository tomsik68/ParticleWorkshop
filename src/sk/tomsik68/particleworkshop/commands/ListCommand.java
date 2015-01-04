package sk.tomsik68.particleworkshop.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import sk.tomsik68.particleworkshop.logic.ParticleTaskData;
import sk.tomsik68.particleworkshop.logic.ParticlesManager;
import sk.tomsik68.permsguru.EPermissions;

public class ListCommand extends CommandHandler {

	public ListCommand(EPermissions perms) {
		super(perms);
		setPermission("pws.ls");
		setArgs("[page]");
		setPlayerOnly(true);
		setDescription("Lists all your particles based on filter");
	}

	@Override
	public void run(CommandSender sender, String[] args) throws Exception {
		int page = 1;
		if (args.length > 0 && isInt(args[0]))
			page = getInt(args[0]);
		List<ParticleTaskData> dataList = ParticlesManager.instance
				.getDataOf(((Player) sender).getUniqueId());
		StringBuilder sb = new StringBuilder();
		for (ParticleTaskData data : dataList) {
			sb = sb.append(data.getNumber()).append('_')
					.append(data.getCount()).append('x')
					.append(data.getParticleName()).append('_')
					.append(data.getLocation().toString()).append('\n');
		}
		sender.sendMessage(ChatPaginator.paginate(sb.toString(), page)
				.getLines());
	}

}
