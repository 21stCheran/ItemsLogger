# ItemsLogger Copilot Instructions

## Project Overview

ItemsLogger is a Minecraft Spigot plugin that logs player inventory states at key events (death, join, quit, restart). It uses MySQL with HikariCP connection pooling and follows a singleton pattern for core services.

## Architecture Patterns

### Core Components

- **`ItemsLogger.java`**: Main plugin class using singleton pattern (`getPlugin()`)
- **`Storage.java`**: Database abstraction layer with HikariCP pooling (singleton)
- **`Config.java`**: Configuration wrapper for Bukkit's FileConfiguration
- **`ItemPlayer.java`**: Data model representing a logged player state

### Service Layer Pattern

All utility classes follow static factory patterns:

```java
Storage storage = Storage.getStorage(plugin);  // Singleton with lazy initialization
Config config = new Config(getConfig());       // Wrapper pattern
```

### Event-Driven Logging

Listeners in `listeners/` package capture Bukkit events and create `ItemPlayer` snapshots:

- Events run async database operations using `BukkitRunnable.runTaskAsynchronously()`
- All logging respects `itemslogger.log` permission check
- Configurable logging types via `config.yml` logTypes section

## Database Integration

### Connection Management

- Uses HikariCP with singleton `Storage` class
- Connection pooling configured in `Storage.connect()` with optimized MySQL settings
- Prepared statements for all queries with parameter binding

### Data Serialization

- Player inventories serialized to Base64 using `Serialize.itemStackArrayToBase64()`
- Location, experience, and timestamps stored as separate fields
- Cause tracking via `Cause` enum (DEATH, JOIN, QUIT, RESTART)

## Command Structure

### Command Registration Pattern

Commands registered in `ItemsLogger.registerCommands()`:

```java
this.getCommand(Cmd.ITEMSLOGGER).setExecutor(new MainCommand());
```

### Command Routing

`MainCommand` uses switch-case routing to delegate to `CommandInterface` implementations:

- Default case routes to `SearchLogs` for inventory queries
- `reload` routes to `ReloadConfig`
- `OpenItemLog` handles separate command registration

## Development Workflow

### Build Process

```bash
mvn clean package  # Creates shaded JAR with dependencies
```

- Maven Shade plugin bundles HikariCP and MySQL connector
- Target: Java 8 compatibility for Minecraft server environments
- Spigot API 1.20.6 as provided dependency

### Configuration Management

- `config.yml` controls database connection and logging behavior
- Debug mode enables detailed logging via `plugin.debugMessage()`
- Automatic log cleanup based on `daysToRetainLogs` setting

### Testing Approach

- Manual testing in Minecraft server environment required
- Database operations should be tested with actual MySQL instance
- Permission testing requires Bukkit permission system

## Key Conventions

### Error Handling

- Database exceptions caught and printed to console
- Plugin continues operation even if individual log saves fail
- Connection timeouts configured with failsafe values

### Async Operations

Always wrap database operations in async tasks:

```java
new BukkitRunnable() {
    @Override
    public void run() {
        storage.saveItemPlayer(itemPlayer);
    }
}.runTaskAsynchronously(plugin);
```

### Permission Checks

All logging operations check `Permissions.PERMISSION_LOG` before execution.

## Development Notes

- Plugin designed for MySQL only (no SQLite fallback)
- Inventory serialization tied to Bukkit API versions
- Server restart handling saves all online players automatically
- Debug messages provide operation tracing when enabled
