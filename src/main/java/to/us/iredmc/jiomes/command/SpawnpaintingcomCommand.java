
package to.us.iredmc.jiomes.command;

import to.us.iredmc.jiomes.procedures.SpawnpaintingcomCommandExecutedProcedure;
import to.us.iredmc.jiomes.JiomesModElements;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.server.ServerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.command.Commands;
import net.minecraft.command.CommandSource;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.arguments.StringArgumentType;

@JiomesModElements.ModElement.Tag
public class SpawnpaintingcomCommand extends JiomesModElements.ModElement {
	public SpawnpaintingcomCommand(JiomesModElements instance) {
		super(instance, 5);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(customCommand());
	}

	private LiteralArgumentBuilder<CommandSource> customCommand() {
		return LiteralArgumentBuilder.<CommandSource>literal("spawnpainting").requires(s -> s.hasPermissionLevel(1))
				.then(Commands.argument("arguments", StringArgumentType.greedyString()).executes(this::execute)).executes(this::execute);
	}

	private int execute(CommandContext<CommandSource> ctx) {
		ServerWorld world = ctx.getSource().getWorld();
		double x = ctx.getSource().getPos().getX();
		double y = ctx.getSource().getPos().getY();
		double z = ctx.getSource().getPos().getZ();
		Entity entity = ctx.getSource().getEntity();
		if (entity == null)
			entity = FakePlayerFactory.getMinecraft(world);
		HashMap<String, String> cmdparams = new HashMap<>();
		int[] index = {-1};
		Arrays.stream(ctx.getInput().split("\\s+")).forEach(param -> {
			if (index[0] >= 0)
				cmdparams.put(Integer.toString(index[0]), param);
			index[0]++;
		});
		{
			Map<String, Object> $_dependencies = new HashMap<>();
			$_dependencies.put("x", x);
			$_dependencies.put("y", y);
			$_dependencies.put("z", z);
			$_dependencies.put("world", world);
			SpawnpaintingcomCommandExecutedProcedure.executeProcedure($_dependencies);
		}
		return 0;
	}
}
