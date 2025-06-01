# Kotlin TODO Tracker - IntelliJ IDEA Plugin

A comprehensive IntelliJ IDEA plugin that automatically scans Kotlin files for TODO comments, highlights them in the editor, and provides a convenient side panel for navigation and management.

## Features

- **Automatic TODO Detection**: Scans all Kotlin files (\`.kt\` and \`.kts\`) for TODO comments
- **Multiple Comment Styles**: Supports line comments (\`// TODO\`) and block comments (\`/* TODO */\`)
- **Inline Highlighting**: Highlights TODO comments directly in the editor with a subtle background color
- **Side Panel Navigation**: Dedicated tool window showing all TODOs with file and line information
- **Click-to-Navigate**: Double-click any TODO in the panel to jump directly to its location
- **Real-time Updates**: Automatically updates the TODO list when files are modified
- **Keyword Filtering**: Filter TODOs by keyword in the side panel
- **Persistent State**: Maintains TODO list between IDE restarts
- **Error Handling**: Robust error handling to ensure smooth IDE operation

## Installation

### From Source

1. Clone this repository:
   \`\`\`bash
   git clone https://github.com/yourusername/kotlin-todo-tracker.git
   cd kotlin-todo-tracker
   \`\`\`

2. Build the plugin:
   \`\`\`bash
   ./gradlew buildPlugin
   \`\`\`

3. Install the plugin:
   - Open IntelliJ IDEA
   - Go to \`File > Settings > Plugins\`
   - Click the gear icon and select \`Install Plugin from Disk...\`
   - Navigate to \`build/distributions/kotlin-todo-tracker-1.0.0.zip\`
   - Restart IntelliJ IDEA

### Development Setup

1. Import the project into IntelliJ IDEA
2. Ensure you have the IntelliJ Platform Plugin SDK configured
3. Run the plugin in a development instance:
   \`\`\`bash
   ./gradlew runIde
   \`\`\`

## Usage

### Viewing TODOs

1. Open any Kotlin file with TODO comments
2. The plugin automatically scans and highlights TODO comments
3. Open the "TODO Tracker" tool window from the right sidebar
4. All TODOs are listed with file name and line number

### Navigation

- **Double-click** any TODO in the side panel to navigate to its location
- **Press Enter** while a TODO is selected to navigate
- The editor will automatically scroll to and highlight the selected TODO

### Filtering

- Use the filter field at the top of the TODO Tracker panel
- Type keywords to filter TODOs containing specific text
- Filter is case-insensitive and searches within TODO text

### Supported TODO Formats

The plugin recognizes these TODO comment patterns:

\`\`\`kotlin
// TODO: This is a line comment TODO
// TODO This also works without colon

/* TODO: Block comment TODO */
/* TODO Block comment without colon */

/*
 * TODO: Multi-line block comment
 * with additional details
 */
\`\`\`

## Architecture

### Core Components

- **TodoParser**: Manual parsing engine for extracting TODO comments from Kotlin files
- **TodoService**: Central service managing TODO cache and notifications
- **TodoToolWindow**: Side panel UI for displaying and filtering TODOs
- **TodoHighlightAnnotator**: Editor annotator for inline highlighting
- **TodoFileListener**: File system listener for real-time updates

### Key Features Implementation

- **Manual Parsing**: Uses regex patterns to identify TODO comments without external libraries
- **Caching**: Efficient caching system to avoid re-parsing unchanged files
- **Thread Safety**: All operations are thread-safe and use IntelliJ's read/write actions
- **Memory Management**: Automatic cleanup of cached data for deleted files

## Development

### Building

\`\`\`bash
./gradlew build
\`\`\`

### Testing

\`\`\`bash
./gradlew test
\`\`\`

### Running in Development

\`\`\`bash
./gradlew runIde
\`\`\`

### Creating Distribution

\`\`\`bash
./gradlew buildPlugin
\`\`\`

## Configuration

The plugin works out of the box with no configuration required. However, you can customize:

- **Highlighting Colors**: Modify \`TodoHighlightAnnotator.java\` to change highlight appearance
- **TODO Patterns**: Update regex patterns in \`TodoParser.java\` for custom TODO formats
- **Tool Window Position**: Change the \`anchor\` attribute in \`plugin.xml\`

## Troubleshooting

### TODOs Not Appearing

1. Ensure the file is a Kotlin file (\`.kt\` or \`.kts\`)
2. Check that TODO comments follow supported formats
3. Try refreshing using \`Tools > Refresh TODOs\`

### Performance Issues

1. The plugin caches parsed results for efficiency
2. Large projects may take a moment for initial scanning
3. File changes trigger incremental updates only

### Plugin Not Loading

1. Verify IntelliJ IDEA version compatibility (2023.2+)
2. Ensure Kotlin plugin is installed and enabled
3. Check IDE logs for error messages

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built using the IntelliJ Platform SDK
- Inspired by the need for better TODO management in Kotlin projects
- Thanks to the IntelliJ Platform documentation and community
\`\`\`
