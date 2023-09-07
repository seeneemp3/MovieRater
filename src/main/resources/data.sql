MERGE INTO ratings_mpa (id, name, description)
    VALUES (1, 'G', 'The movie has no age restrictions'),
           (2, 'PG', 'Recommended for children to watch with parents'),
           (3, 'PG-13', 'Not recommended for children under 13'),
           (4, 'R', 'Viewing by individuals under 17 is allowed only in the presence of an adult'),
           (5, 'NC-17', 'Viewing by individuals under 18 is prohibited');

MERGE INTO genres (id, name)
    VALUES (1, 'Comedy'),
           (2, 'Drama'),
           (3, 'Animation'),
           (4, 'Thriller'),
           (5, 'Documentary'),
           (6, 'Action');