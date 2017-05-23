# lein-umlaut

CLI for umlaut. Please read `lein help umlaut`.

## Usage

Put `[lein-umlaut "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `project.clj`.

Usage examples:
```
lein umlaut graphql ./schemas out.graphql
lein umlaut dot ./schemas ./diagrams
lein umlaut lacinia ./schemas lacinia.edn
```

## Help?
`lein help umlaut`
