public class NBody {
    public static double readRadius(String fileName) {
        In in = new In(fileName);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        double redius = in.readDouble();
        Planet[] planets = new Planet[n];
        for (int i = 0; i < n; i++) {
            planets[i] = new Planet(
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readDouble(),
                    in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("please enter 3 command args");
            return;
        }

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = String.format("%s", args[2]);

        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);

        for (double time = 0; time <= T; time = time + dt) {
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            // for (int i = 0; i < planets.length; i++) {
            // for (int j = i + 1; j < planets.length; j++) {
            // xForces[i] = xForces[i] + planets[i].calcForceExertedByX(planets[j]);
            // xForces[j] = xForces[j] - planets[i].calcForceExertedByX(planets[j]);
            // yForces[i] = yForces[i] + planets[i].calcForceExertedByY(planets[j]);
            // yForces[j] = yForces[j] - planets[i].calcForceExertedByY(planets[j]);
            // }
            // planets[i].update(dt, xForces[i], yForces[i]);
            // }
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                planets[i].update(dt, xForces[i], yForces[i]);
            }
        }

        StdDraw.clear();
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
