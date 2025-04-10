import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { AuthProvider } from "react-oidc-context";
import { App } from "./App.tsx";
import "./index.scss";

const oidcConfig = {
  authority: "http://localhost:8090/realms/invernada",
  client_id: "invernada-web",
  redirect_uri: "http://localhost:5173/",
  post_logout_redirect_uri: "http://localhost:5173/",
  scope: "openid profile email phone"
};

createRoot(document.getElementById("root") as HTMLElement).render(
  <StrictMode>
    <AuthProvider {...oidcConfig}>
      <App />
    </AuthProvider>
  </StrictMode >
);
