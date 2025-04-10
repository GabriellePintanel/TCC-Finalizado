import { useState, useEffect } from 'react';
import { useAuth } from "react-oidc-context";

interface ResponseAPI {
  id: string,
  username: string,
  fullName: string,
  givenName: string,
  familyName: string,
  email: string,
  emailVerified: boolean,
  document: string,
  phone: string,
  phoneVerified: boolean
}

export const ProfilePage = () => {
  const auth = useAuth();
  const [profile, setProfile] = useState<ResponseAPI>();

  useEffect(() => {
    (async () => {
      try {
        const token = auth.user?.access_token;
        const response = await fetch("http://localhost:8080/api/profiles", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setProfile(await response.json());
      } catch (e) {
        console.error(e);
      }
    })();
  }, [auth]);

  if (auth.isLoading) {
    return <div>Signing you in/out...</div>;
  }

  if (!auth.isAuthenticated) {
    return <div>Unable to log in</div>;
  }

  return (
    <>
      <h1>Profile Page</h1>
      <p>Id: {profile?.id}</p>
      <p>username: {profile?.username}</p>
      <p>fullName: {profile?.fullName}</p>
      <p>email: {profile?.email}</p>
      <p>document: {profile?.document}</p>
      <p>phone: {profile?.phone}</p>
    </>
  )
}
