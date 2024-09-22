package io.github.username.project

import org.bukkit.Bukkit
import org.bukkit.event.server.PluginEnableEvent
import org.bukkit.plugin.Plugin
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent

/**
 * TabooLibScanner
 * io.github.username.project.TabooLibScanner
 *
 * @author mical
 * @since 2024/9/22 18:11
 */
object TabooLibScanner {

    @Awake(LifeCycle.ACTIVE)
    fun onActive() {
        Bukkit.getPluginManager().plugins.filterNot { usingTabooLib(it) }.forEach {
            it.onDisable()
            Bukkit.getPluginManager().disablePlugin(it)
        }
    }

    @SubscribeEvent
    fun e(e: PluginEnableEvent) {
        if (!usingTabooLib(e.plugin)) {
            e.plugin.onDisable()
            Bukkit.getPluginManager().disablePlugin(e.plugin)
        }
    }

    /**
     * 检测插件是否基于 TabooLib 开发
     */
    private fun usingTabooLib(plugin: Plugin): Boolean {
        return using5(plugin) || using6(plugin)
    }

    /**
     * 检测插件是否基于 TabooLib 5.X 开发
     */
    private fun using5(plugin: Plugin): Boolean {
        return plugin.description.main.endsWith(".boot.PluginBoot") &&
                plugin.javaClass.declaredFields.any { it.name == "tabooLibFile" }
    }

    /**
     * 检测插件是否基于 TabooLib 6.X 开发
     */
    private fun using6(plugin: Plugin): Boolean {
        return plugin.description.main.endsWith(".taboolib.platform.BukkitPlugin") &&
                plugin.javaClass.declaredFields.any { it.name == "pluginInstance" } &&
                plugin.javaClass.declaredFields.any { it.name == "instance" }
//                plugin.javaClass.declaredMethods.any { it.name == "invokeActive" } && // 6.1.0
//                plugin.javaClass.declaredMethods.any { it.name == "injectIllegalAccess" } // 6.0.10
    }
}