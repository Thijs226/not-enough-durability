# GitHub Actions Workflows

This directory contains automated build workflows for the Not Enough Durability mod.

## Workflows

### 1. Build Mod (`build.yml`)

**Triggers:**
- Push to `main` branch
- Push to any `copilot/**` branch
- Pull requests to `main`
- Manual trigger via GitHub Actions tab

**What it does:**
- Checks out the code
- Sets up Java 17
- Builds the mod using Gradle
- Uploads the compiled `.jar` file as an artifact
- On failure, uploads build logs for debugging

**Accessing Build Artifacts:**
1. Go to the "Actions" tab in GitHub
2. Click on the workflow run
3. Scroll down to "Artifacts" section
4. Download `not-enough-durability-mod.zip`

### 2. Release Mod (`release.yml`)

**Triggers:**
- Push a git tag matching `v*.*.*` (e.g., `v1.0.0`, `v1.2.3`)
- Manual trigger via GitHub Actions tab

**What it does:**
- Builds the mod
- Creates a GitHub Release
- Attaches the compiled `.jar` file to the release
- Generates release notes automatically

**Creating a Release:**
```bash
# Tag your commit
git tag v1.0.0

# Push the tag
git push origin v1.0.0
```

The workflow will automatically create a release with the mod file attached.

## Requirements

- Repository must have GitHub Actions enabled (enabled by default)
- For releases: Repository must allow workflow to create releases (default)

## Build Cache

Both workflows use Gradle caching to speed up builds. The first build will take longer as it downloads dependencies, but subsequent builds will be much faster.
