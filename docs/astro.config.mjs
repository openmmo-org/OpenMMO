// @ts-check
import { defineConfig } from "astro/config";
import starlight from "@astrojs/starlight";

const canonical = process.env.READTHEDOCS_CANONICAL_URL;
const url = canonical ? new URL(canonical) : undefined;

export default defineConfig({
  site: url?.origin,
  base: url?.pathname,
  integrations: [
    starlight({
      title: "OpenMMO",
      social: [
        {
          icon: "github",
          label: "GitHub",
          href: "https://github.com/openmmo-org/OpenMMO",
        },
      ],
      sidebar: [
        {
          label: "Concepts",
          items: [{ autogenerate: { directory: "concepts" } }],
        },
        { label: "Guides", items: [{ autogenerate: { directory: "guides" } }] },
      ],
    }),
  ],
});
