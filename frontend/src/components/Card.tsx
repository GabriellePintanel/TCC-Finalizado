import { ReactNode } from "react";
import { Link } from "react-router-dom";

type ProjectCardProps = {
  title: string;
  photo: string;
  url: string;
  children: ReactNode;
};

export const ProjectCard = ({
  title,
  photo,
  url,
  children,
}: ProjectCardProps) => {
  return (
    <div className="card">
      <div className="card-image">
        <figure className="image">
          <img src={photo} alt="Placeholder image" />
        </figure>
      </div>
      <div className="card-content">
        <div className="media">
          <div className="media-content">
            <p className="title is-4">{title}</p>
          </div>
        </div>

        <div className="content">
          {children}
          <br />
          <Link to={url}>Saiba mais</Link>
        </div>
      </div>
    </div>
  );
};
